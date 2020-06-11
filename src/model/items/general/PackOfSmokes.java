package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import sounds.Sound;

import java.util.ArrayList;

/**
 * Created by erini02 on 25/08/16.
 */
public class PackOfSmokes extends GameItem {
    public PackOfSmokes() {
        super("Pack of Smokes", 0.1, true, 20);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return  new Sprite("packofsmokes", "cigarettes.png", 0, this);
    }

    @Override
    public GameItem clone() {
        return new PackOfSmokes();
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new SmokeAction());
    }

    @Override
    public Sound getDropSound() {
        return new Sound("matchbox_drop");
    }

    @Override
    public Sound getPickUpSound() {
        return new Sound("matchbox_pickup");
    }
}
