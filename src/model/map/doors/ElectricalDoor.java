package model.map.doors;

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

import java.util.List;

public abstract class ElectricalDoor extends Door {

    private DoorMechanism doorMechanism;

    public ElectricalDoor(double x, double y, String name, int fromID, int toID) {
        super(x, y, name, fromID, toID);
        doorMechanism = new DoorMechanism(this);
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


    public DoorMechanism getDoorMechanism() {
        return doorMechanism;
    }


    public boolean isDamaged() {
        if (doorMechanism == null) {
            return false;
        }
        return getBreakableObject().getHealth() < getBreakableObject().getMaxHealth();
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
        if (!forWhom.isAI()) {
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
        at.add(new CloseFireDoorAction(this));
        return at;
    }

    protected void thisJustBroke(GameData gameData) {

    }


    public BreakableObject getBreakableObject() {
        return doorMechanism;
    }

    protected void setDoorMechanism(DoorMechanism dm) {
        this.doorMechanism = dm;
    }

    public void shutFireDoor(GameData gameData) {
        Room from = null;
        try {
            from = gameData.getRoomForId(getFromId());
            Room to = gameData.getRoomForId(getToId());
            FireDoor theFireDoor = shutFireDoor(from, to);
            from.addEvent(new ShutFireDoorAnimationEvent(gameData, from, theFireDoor));
            to.addEvent(new ShutFireDoorAnimationEvent(gameData, to, theFireDoor));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public FireDoor shutFireDoor(Room from, Room to) {
        GameMap.separateRooms(to, from);
        FireDoor theFireDoor = wrapInFireDoor(to, this);
        if (theFireDoor == null) {
            theFireDoor = wrapInFireDoor(from, this);
        }
        return theFireDoor;
    }

    private FireDoor wrapInFireDoor(Room room, Door targetDoor) {
        for (int i = 0; i < room.getDoors().length; ++i) {
            if (room.getDoors()[i] == targetDoor) {
                FireDoor newDoor = new FireDoor(targetDoor);
                room.getDoors()[i] = newDoor;
                return newDoor;
            }
        }
        return null;
    }


}
