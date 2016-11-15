package model.items;

import model.Actor;
import model.GameData;
import model.Target;
import model.events.ambient.ElectricalFire;
import model.events.damage.Damager;
import model.items.foods.ExplodingFood;
import model.items.general.ExplodableItem;
import model.items.general.GameItem;
import model.map.Room;
import util.MyRandom;

/**
 * Created by erini02 on 15/11/16.
 */
public class MolotovCocktail extends GameItem implements Damager, ExplodableItem {
    private final GameItem inner;

    public MolotovCocktail(GameItem selectedBurnable) {
        super("Molotov Cocktail", selectedBurnable.getWeight(), false);
        inner = selectedBurnable;
    }

    @Override
    public String getText() {
        return "A molotov cocktail exploded";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return MyRandom.nextDouble() < 0.5;
    }

    @Override
    public String getName() {
        return inner.getBaseName();
    }

    @Override
    public void doDamageOnMe(Target target) {
        if (target instanceof Actor) {
            ((Actor)target).subtractFromHealth(0.5);
        }
    }

    @Override
    public GameItem getAsItem() {
        return this;
    }

    @Override
    public void explode(GameData gameData, Room room, Actor maker) {
        for (Actor a : room.getActors()) {
            a.getAsTarget().beExposedTo(maker, this);
        }
        ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
        fire.startNewEvent(room);
    }

    @Override
    public void setConceledWithin(ExplodingFood explodingFood) {

    }

    @Override
    public GameItem clone() {
        return new MolotovCocktail(inner);
    }
}
