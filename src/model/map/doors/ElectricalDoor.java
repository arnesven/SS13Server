package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.roomactions.AttackDoorAction;
import model.actions.roomactions.CloseFireDoorAction;
import model.actions.roomactions.RepairDoorAction;
import model.actions.roomactions.ShowDoorHackingFancyFrameAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.ElectricalMachinery;
import util.HTMLText;

import java.util.List;

public abstract class ElectricalDoor extends Door {

    private DoorMechanism doorMechanism;
    private DoorState doorState;

    public ElectricalDoor(double x, double y, String name, int fromID, int toID, boolean locked) {
        super(x, y, name, fromID, toID);
        doorMechanism = new DoorMechanism(this);
        if (locked) {
            doorState = new DoorState.Locked(this);
        } else {
            doorState = new DoorState.Normal(this);
        }
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
            if (forWhom instanceof Player) {
                at.add(new ShowDoorHackingFancyFrameAction(gameData, forWhom, this));
            }
        }
        if (GameItem.hasAnItemOfClass(forWhom, Tools.class) && isDamaged()) {
            at.add(new RepairDoorAction(gameData, forWhom, this));
        }
        if (getDoorMechanism().getFireCord().isOK() || !forWhom.isAI()) {
            at.add(new CloseFireDoorAction(this));
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
            from.addEvent(new ShutFireDoorAnimationEvent(gameData, from, theFireDoor));
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
        if (!(doorState.getOldState() instanceof DoorState.Locked)) {
            GameMap.joinRooms(from, to);
        }
        this.doorState = doorState.getOldState();
    }

    public void goUnpowered(Room from, Room to) {
        GameMap.separateRooms(from, to);
        this.doorState = new DoorState.Unpowered(this);
        this.doorMechanism.getPowerLine().setState(0);
        this.doorMechanism.getBackupLine().setState(0);
    }

    public void gotRepaired(GameData gameData) {
        this.doorState = doorState.getOldState();
    }
}
