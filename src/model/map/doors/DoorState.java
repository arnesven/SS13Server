package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.ForceOpenDoorAction;
import model.actions.objectactions.ForceOpenDoorAndMoveThrough;
import model.actions.roomactions.*;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.items.general.UniversalKeyCard;
import model.items.general.Tools;
import model.items.weapons.Crowbar;
import util.HTMLText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class DoorState implements Serializable {

    protected final ElectricalDoor door;
    private DoorState oldState;

    protected DoorState(ElectricalDoor door) {
        this.door = door;
        oldState = door.getDoorState();
    }

    protected abstract List<Action> getActions(GameData gameData, Actor forWhom);
    protected abstract Sprite getSprite();

    protected DoorMechanism getDoorMechanism() {
        return door.getDoorMechanism();
    }

    public DoorState getOldState() {
        return oldState;
    }

    public abstract String getDiodeColor();

    public static class Normal extends DoorState {
        public Normal(ElectricalDoor electricalDoor) {
            super(electricalDoor);
        }

        @Override
        protected List<Action> getActions(GameData gameData, Actor forWhom) {
            List<Action> at = new ArrayList<>();
            KeyCard kc = UniversalKeyCard.findKeyCard(forWhom);

            if (getDoorMechanism().permitsLock() && !door.isBroken() && ((kc != null && kc.canOpenDoor(door)) || forWhom.isAI())) {
                at.add(new LockDoorAction(door));
                at.add(new MoveThroughAndLock(door));
            }
            if (getDoorMechanism().getFireCord().isOK() || !forWhom.isAI()) {
                at.add(new MoveThroughAndCloseFireDoorAction(door));
            }
            return at;
        }

        @Override
        protected Sprite getSprite() {
            return door.getNormalSprite();
        }

        @Override
        public String getDiodeColor() {
            return HTMLText.makeText("black", "#0bf900","GREEN");
        }
    }

    public static class Locked extends DoorState {
        public Locked(ElectricalDoor electricalDoor) {
            super(electricalDoor);
        }

        @Override
        protected List<Action> getActions(GameData gameData, Actor forWhom) {
            List<Action> at = new ArrayList<>();
            KeyCard kc = UniversalKeyCard.findKeyCard(forWhom);

            if (getDoorMechanism().permitsUnlock() &&
                    ((kc != null && kc.canOpenDoor(door)) || forWhom.isAI())) {
                at.add(new UnLockDoorAction(door));
                at.add(new UnLockAndMoveThroughAction(door));
            }
            return at;
        }

        @Override
        protected Sprite getSprite() {
            return door.getLockedSprite();
        }

        @Override
        public String getDiodeColor() {
            return HTMLText.makeText("black", "red", "__RED__");
        }
    }

    public static class Broken extends DoorState {
        public Broken(ElectricalDoor electricalDoor) {
            super(electricalDoor);
        }

        @Override
        protected List<Action> getActions(GameData gameData, Actor forWhom) {
            return new ArrayList<>();
        }

        @Override
        protected Sprite getSprite() {
            return door.getBrokenSprite();
        }

        @Override
        public String getDiodeColor() {
            return HTMLText.makeText("white", "gray", "_DARK_");
        }
    }

    public static class Unpowered extends DoorState {
        protected Unpowered(ElectricalDoor door) {
            super(door);
        }

        @Override
        protected List<Action> getActions(GameData gameData, Actor forWhom) {
            List<Action> at = new ArrayList<>();
            if (ForceOpenDoorAction.hasAnApplicableItem(forWhom)) {
                at.add(new ForceOpenDoorAction(door));
                at.add(new ForceOpenDoorAndMoveThrough(door));
            }
            return at;
        }

        @Override
        protected Sprite getSprite() {
            return door.getUnpoweredSprite();
        }


        @Override
        public String getDiodeColor() {
            return HTMLText.makeText("white", "gray", "_DARK_");
        }
    }
}
