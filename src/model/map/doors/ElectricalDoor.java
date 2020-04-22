package model.map.doors;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.roomactions.AttackDoorAction;
import model.actions.roomactions.CloseFireDoorAction;
import model.actions.roomactions.MoveThroughAndCloseFireDoorAction;
import model.actions.roomactions.RepairDoorAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.FireDamage;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.Repairable;

import java.util.ArrayList;
import java.util.List;

public abstract class ElectricalDoor extends Door {

    private BreakableObject breakableObject;


    public ElectricalDoor(double x, double y, String name, int fromID, int toID) {
        super(x, y, name, fromID, toID);
        breakableObject = new DoorMechanism();
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

    public boolean isDamaged() {
        if (breakableObject == null) {
            return false;
        }
        return getBreakableObject().getHealth() < getBreakableObject().getMaxHealth();
    }

    public boolean isBroken() {
        if (breakableObject == null) {
            return false;
        }
        return breakableObject.isBroken();
    }

    @Override
    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        if (!forWhom.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter)) {
            AttackAction act = new AttackDoorAction(forWhom, this);
            act.addTarget(breakableObject);
            act.stripAllTargetsBut(breakableObject);
            act.addClientsItemsToAction(forWhom);
            at.add(act);
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
        return breakableObject;
    }

    protected void setBreakableObject(BreakableObject breakableObject) {
        this.breakableObject = breakableObject;
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


    private class DoorMechanism extends BreakableObject implements Repairable {

        public DoorMechanism() {
            super(ElectricalDoor.this.getName(), 2.0, null);
        }

        @Override
        protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

        }

        @Override
        public void thisJustBroke(GameData gameData) {
            ElectricalDoor.this.thisJustBroke(gameData);
        }

        @Override
        public boolean canBeInteractedBy(Actor performingClient) {
            return performingClient.getPosition().getID() == getFromId() || performingClient.getPosition().getID() == getToId();
        }

        @Override
        public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
            if (damage instanceof FireDamage) {
                damage = new FireDamage(0.25);
            }
            super.beExposedTo(performingClient, damage, gameData);
        }

        @Override
        public void doWhenRepaired(GameData gameData) {

        }
    }
}
