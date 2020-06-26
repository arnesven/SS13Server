package model.items.tools;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.actions.itemactions.DetachAction;
import model.items.general.GameItem;

import java.util.ArrayList;

public class Blowtorch extends GameItem {
    public Blowtorch() {
        super("Blowtorch", 0.2, true, 52);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("blowtorch", "items.png", 31, this);
    }

    @Override
    public GameItem clone() {
        return new Blowtorch();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A handy tool for cutting through metal and other materials. Useful for salvaging and detaching stuff.";
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        TargetingAction detatchAction = new DetachAction(cl);
        if (detatchAction.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(detatchAction);
        }

    }
}
