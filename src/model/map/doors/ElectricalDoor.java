package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.GameState;
import model.Player;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.roomactions.AttackDoorAction;
import model.actions.roomactions.CloseFireDoorAction;
import model.actions.roomactions.RepairDoorAction;
import model.actions.roomactions.ShowDoorHackingFancyFrameAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.events.animation.AnimationEvent;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.ElectricalMachinery;
import util.HTMLText;
import util.Logger;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ElectricalDoor extends Door {

    private DoorMechanism doorMechanism;
    private DoorState doorState;

    public ElectricalDoor(double x, double y, double z, String name, int fromID, int toID, boolean locked) {
        super(x, y, z, name, fromID, toID);
        doorMechanism = new DoorMechanism(this);
        if (locked) {
            doorState = new DoorState.Locked(this);
        } else {
            doorState = new DoorState.Normal(this);
        }
    }

    public ElectricalDoor(double x, double y, String name, int fromID, int toID, boolean locked) {
        this(x, y, 0.0, name, fromID, toID, locked);
    }



    protected Sprite getFogOfWarSprite() {
        return getUnpoweredSprite();
    }


    @Override
    public boolean requiresPower() {
        return true;
    }

    @Override
    public ElectricalMachinery getElectricalLock(GameData gameData) {
        return doorMechanism;
    }

    @Override
    public String getName() {
        if (isBroken()) {
            return super.getName() + " (broken)";
        } else if (isDamaged()) {
            return super.getName() + " (damaged)";
        }
        return super.getName();
    }


    @Override
    protected Sprite getSprite() {
        if (isAnimating()) {
            return Sprite.blankSprite();

        } else if (doorState instanceof DoorState.Normal || doorState instanceof DoorState.Locked) {
            if (getDoorMechanism() != null) {
                if (getDoorMechanism().hasError()) {
                    return getErrorSprite();
                }
            }
        }
        return doorState.getSprite();
    }

    public DoorMechanism getDoorMechanism() {
        return doorMechanism;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public boolean isDamaged() {
        if (doorMechanism == null) {
            return false;
        }
        return doorMechanism.getHealth() < doorMechanism.getMaxHealth();
    }

    public boolean isBroken() {
        if (doorMechanism == null) {
            return false;
        }
        return doorMechanism.isBroken();
    }

    @Override
    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        if (!forWhom.isAI() || !isBroken()) {
            AttackAction act = new AttackDoorAction(forWhom, this);
            act.addTarget(doorMechanism);
            act.stripAllTargetsBut(doorMechanism);
            act.addClientsItemsToAction(forWhom);
            at.add(act);
            if (forWhom instanceof Player && forWhom.isIntelligentCreature()) {
                at.add(new ShowDoorHackingFancyFrameAction(gameData, (Player)forWhom, this));
            }
        }
        if (GameItem.hasAnItemOfClass(forWhom, Tools.class) && isDamaged()) {
            at.add(new RepairDoorAction(gameData, forWhom, this));
        }
        if (forWhom.isIntelligentCreature()) {
            if (getDoorMechanism().getFireCord().isOK() || !forWhom.isAI()) {
                at.add(new CloseFireDoorAction(this));
            }
        }

        at.addAll(doorState.getActions(gameData, forWhom));
        return at;
    }

    public void breakDoor(GameData gameData) {
        this.doorState = new DoorState.Broken(this);
        try {
            GameMap.joinRooms(gameData.getRoomForId(getFromId()), gameData.getRoomForId(getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }


    private void setDoorMechanism(DoorMechanism dm) {
        this.doorMechanism = dm;
        dm.setDoor(this);
    }

    public void shutFireDoor(GameData gameData) {
        Room from = null;
        try {
            from = gameData.getRoomForId(getFromId());
            Room to = gameData.getRoomForId(getToId());
            FireDoor theFireDoor = shutFireDoor(from, to);
            AnimationEvent ae = new ShutFireDoorAnimationEvent(gameData, from, theFireDoor);
            from.addEvent(ae);
            to.addEvent(new ShutFireDoorAnimationEvent(gameData, to, theFireDoor));
            getDoorMechanism().getFireCord().setState(1);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private FireDoor shutFireDoor(Room from, Room to) {
        GameMap.separateRooms(to, from);
        FireDoor theFireDoor = wrapInFireDoor(to, this);
        if (theFireDoor == null) {
            theFireDoor = wrapInFireDoor(from, this);
        }
        return theFireDoor;
    }

    private FireDoor wrapInFireDoor(Room room, ElectricalDoor targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                FireDoor newDoor = new FireDoor(targetDoor);
                room.getDoors()[i] = newDoor;
                return newDoor;
            }
        }
        return null;
    }

    public String getDiodeColor() {
        if (doorState instanceof DoorState.Locked || doorState instanceof DoorState.Normal) {
            if (getDoorMechanism() != null && getDoorMechanism().hasError()) {
                return HTMLText.makeText("black", "yellow", "YELLOW");
            }
        }
        Logger.log("Getting diode color, Door state is: " + doorState);
        return doorState.getDiodeColor();
    }

    public abstract Sprite getLockedSprite();

    public abstract Sprite getNormalSprite();

    public abstract Sprite getBrokenSprite();

    public abstract Sprite getUnpoweredSprite();

    public abstract Sprite getErrorSprite();


    public void unlockRooms(Room from, Room to) {
        GameMap.joinRooms(from, to);
        this.doorState = new DoorState.Normal(this);
    }

    public void lockRooms(Room from, Room to) {
        GameMap.separateRooms(to, from);
        this.doorState = new DoorState.Locked(this);
        this.doorMechanism.getLockCord().setState(1);
    }

    public void crowbarOpen(Room from, Room to) {
        GameMap.joinRooms(to, from);
        this.doorState = new DoorState.Broken(this);
        this.getDoorMechanism().setHealth(0.0);
    }


    public void goPowered(Room from, Room to) {
        if (doorState.getOldState() instanceof DoorState.Locked) {
            GameMap.separateRooms(from, to);
        } else {
            GameMap.joinRooms(from, to);
        }
        if (doorState instanceof DoorState.Unpowered) {
            this.doorState = doorState.getOldState();
        }
    }

    public void goUnpowered(Room from, Room to) {
        GameMap.separateRooms(from, to);
        if (!(doorState instanceof DoorState.Unpowered)) {
            this.doorState = new DoorState.Unpowered(this);
            this.doorMechanism.getPowerLine().setState(0);
            this.doorMechanism.getBackupLine().setState(0);
        }
    }

    public void gotRepaired(GameData gameData) {
        this.doorState = doorState.getOldState(); // from broken to.... (Unpowered, Normal or Locked)
    }

    public static Map<String, ElectricalDoor> getBuildableDoors() {
        Map<String, ElectricalDoor> result = new HashMap<>();
        result.put("Normal", new NormalDoor(0, 0, 0, 0, 0, false));
        result.put("Security", new SecurityDoor(0, 0, 0, 0, false));
        result.put("Engineering", new EngineeringDoor(0, 0, 0, 0, false));
        result.put("Science", new ScienceDoor(0, 0, 0, 0, false));
        result.put("Command", new CommandDoor(0, 0, 0, 0, false));
        return result;
    }

    public abstract ElectricalDoor makeCopy(double x, double y, double z, int fromId, int toId);
}
