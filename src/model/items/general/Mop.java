package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;

import java.util.ArrayList;

public class Mop extends GameItem {
    public Mop() {
        super("Mop", 0.4, false, 20);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        Action a = new CleanUpBloodAction();
        at.add(a);

    }

    @Override
    public GameItem clone() {
        return new Mop();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("mop", "janitor.png", 3);
    }

}
