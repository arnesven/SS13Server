package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.MakeMolotovAndThrowAction;

import java.util.ArrayList;

/**
 * Created by erini02 on 15/11/16.
 */
public class ZippoLighter extends LightItem {
    private int uses;

    public ZippoLighter() {
        super("Zippo Lighter", 0.1, false, 45);
        this.uses = 8;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("zippo", "items2.png", 3, 4, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        Action molotov = new MakeMolotovAndThrowAction(cl);
        if (molotov.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(molotov);
        }
    }

    @Override
    public GameItem clone() {
        return new ZippoLighter();
    }
}
