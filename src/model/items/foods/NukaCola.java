package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.events.damage.RadiationDamage;
import sounds.Sound;
import util.MyRandom;

public class NukaCola extends JunkHealingFood {

    private double RAD_DAMAGE_CHANCE = 0.25;

    public NukaCola(Actor maker) {
        super("NukaCola", 0.33, maker, 2);
    }

    @Override
    public double getFireRisk() {
        return 0.01;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("nukacolacan", "drinks.png", 10, 8, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A slightly irradiated carbonated soda beverage.";
    }

    @Override
    public FoodItem clone() {
        return new NukaCola(getMaker());
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        super.triggerSpecificReaction(eatenBy, gameData);
        if (MyRandom.nextDouble() < RAD_DAMAGE_CHANCE) {
            eatenBy.getAsTarget().beExposedTo(eatenBy, new RadiationDamage(0.5, gameData), gameData);
        }
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        int i = MyRandom.nextInt(3) + 1;
        return new Sound("can_open" + i);
    }
}
