package model.items.chemicals;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.foods.FoodItem;
import model.items.general.Chemicals;

public class GeneratorStartedFluid extends Chemicals {
    public GeneratorStartedFluid() {
        super("Generator Started Fluid", 20);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("generatorstarterfluid", "tank.png", 2, 0, this);
    }

    @Override
    public boolean isFlammable() {
        return true;
    }

    @Override
    public boolean isToxic() {
        return true;
    }

    @Override
    public String getFormula() {
        return "C16H3BO2";
    }

    @Override
    public FoodItem clone() {
        return new GeneratorStartedFluid();
    }
}