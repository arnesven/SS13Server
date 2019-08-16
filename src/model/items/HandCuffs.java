package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.PutHandCuffsOnAction;
import model.items.general.GameItem;

import java.util.ArrayList;

/**
 * Created by erini02 on 21/11/16.
 */
public class HandCuffs extends GameItem {
    public HandCuffs() {
        super("Handcuffs", 0.3, false, 120);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("handcuffs", "items.png", 47, this);
    }


    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData,at,cl);
        PutHandCuffsOnAction put = new PutHandCuffsOnAction(cl);
        if (put.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(put);
        }
    }

    @Override
    public GameItem clone() {
        return new HandCuffs();
    }
}
