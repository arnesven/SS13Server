package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.roomactions.AttackDoorAction;
import model.actions.roomactions.ShowDoorHackingFancyFrameAction;

import java.util.ArrayList;
import java.util.List;

public class MagicWallDoor extends ElectricalDoor {

    private final Door oldDoor;

    public MagicWallDoor(Door oldDoor) {
        super(oldDoor.getX(), oldDoor.getY(), "Magic Wall", oldDoor.getFromId(), oldDoor.getToId(), true);
        this.oldDoor = oldDoor;
    }

    @Override
    public Sprite getLockedSprite() {
        return new Sprite("normalmagicwall", "wizardstuff.png", 0, 5, this);
    }

    @Override
    public Sprite getNormalSprite() {
        return new Sprite("normalmagicwall", "wizardstuff.png", 0, 5, this);
    }

    @Override
    public Sprite getBrokenSprite() {
        return new Sprite("brokenmagicwall", "wizardstuff.png", 1, 5, this);
    }

    @Override
    public Sprite getUnpoweredSprite() {
        return new Sprite("normalmagicwall", "wizardstuff.png", 0, 5, this);
    }

    @Override
    public Sprite getErrorSprite() {
        return new Sprite("normalmagicwall", "wizardstuff.png", 0, 5, this);
    }

    @Override
    public ElectricalDoor makeCopy(double x, double y, double z, int fromId, int toId) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Sprite getFogOfWarSprite() {
        return oldDoor.getFogOfWarSprite();
    }

    @Override
    public boolean requiresPower() {
        return false;
    }

    @Override
    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = new ArrayList<>();
        if (!forWhom.isAI() || !isBroken()) {
            AttackAction act = new AttackDoorAction(forWhom, this);
            act.addTarget(getDoorMechanism());
            act.stripAllTargetsBut(getDoorMechanism());
            act.addClientsItemsToAction(forWhom);
            at.add(act);
        }
        return at;
    }
}
