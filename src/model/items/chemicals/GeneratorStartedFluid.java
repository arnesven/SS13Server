package model.items.chemicals;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.BoostGeneratorAction;
import model.items.foods.FoodItem;
import model.objects.power.PositronGenerator;

import java.util.List;

public class GeneratorStartedFluid extends Chemicals {
    public GeneratorStartedFluid() {
        super("Generator Starter Fluid", 20);
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
    public boolean isCorrosive() {
        return false;
    }

    @Override
    public String getFormula() {
        return "C16H3BO2";
    }

    @Override
    public FoodItem clone() {
        return new GeneratorStartedFluid();
    }

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> acts =  super.getInventoryActions(gameData, forWhom);
        if (PositronGenerator.roomHasGenerator(forWhom.getPosition()) != null) {
            acts.add(new BoostGeneratorAction());
        }
        return acts;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A chemical for boosting the generator's power output.";
    }
}
