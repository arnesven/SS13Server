package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.LoadWeaponAction;
import model.items.general.GameItem;

import java.util.ArrayList;

/**
 * Created by erini02 on 17/11/16.
 */
public abstract class Ammunition extends GameItem {

    public Ammunition(String string, double weight, int cost) {
        super(string, weight, true, cost);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        LoadWeaponAction act = new LoadWeaponAction(this);
        if (act.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(act);
        }

    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("ammo", "ammo.png", 2, this);
    }

    public abstract boolean canBeLoadedIntoGun(AmmoWeapon w);

    public abstract void loadIntoGun(AmmoWeapon selected);
}
