package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.ReviveAction;
import model.characters.general.HumanCharacter;
import model.map.Room;

import java.util.ArrayList;

/**
 * Created by erini02 on 02/09/16.
 */
public class Defibrilator extends GameItem {

    private boolean charge = true;

    public Defibrilator() {
        super("Defibrillator", 1.0, false);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (charge) {
            return new Sprite("defibrilatorcharged", "device.png", 61);
        }
        return new Sprite("defibrilatoruncharged", "device.png", 58);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
        if (isDeadGuyInRoom(cl.getPosition()) && charge) {
            at.add(new ReviveAction(gameData, cl, this));
        }

    }

    private boolean isDeadGuyInRoom(Room position) {
        for (Actor a : position.getActors()) {
            if (a.getCharacter() instanceof HumanCharacter) {
                if (a.isDead()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GameItem clone() {
        return new Defibrilator();
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }
}
