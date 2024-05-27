package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import sounds.Sound;

public class SpaceCheetos extends JunkHealingFood {
    public SpaceCheetos(Actor maker) {
        super("Space Cheetos", 0.2, maker, 6);
    }

    @Override
    public double getFireRisk() {
        return 0.0;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spacecheetosbag", "food.png", 9, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A salty, spicy and crunchy snack. Ubiquitous across the galaxy.";
    }

    @Override
    public FoodItem clone() {
        return new SpaceCheetos(getMaker());
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return super.getCrunchySound();
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
