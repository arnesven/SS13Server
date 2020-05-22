package model.items.general;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.itemactions.ThrowGrenadeAction;
import model.events.damage.Damager;
import model.items.foods.ExplodingFood;
import model.map.rooms.Room;

import java.util.ArrayList;

public abstract class GrenadeItem extends GameItem implements Damager, ExplodableItem  {

    public GrenadeItem(String string, double weight, boolean usableFromFloor, int cost) {
        super(string, weight, usableFromFloor, cost);
    }

    public GrenadeItem(String grenade, double v, int i) {
        super(grenade, v, i);
    }


    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new ThrowGrenadeAction(cl, this));
    }

    @Override
    public GameItem getAsItem() {
        return this;
    }

    public abstract void doExplosionAction(Room targetRoom, GameData gameData, Actor performingClient);
}
