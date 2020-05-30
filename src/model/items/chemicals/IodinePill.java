package model.items.chemicals;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.LimitedRadiationProtection;
import model.items.foods.FoodItem;
import util.MyRandom;

public class IodinePill extends Chemicals {
    public IodinePill() {
        super("Iodine Pill", 9);
        setCost(10);
    }

    @Override
    public boolean isFlammable() {
        return false;
    }

    @Override
    public boolean isToxic() {
        return false;
    }

    @Override
    public boolean isCorrosive() {
        return false;
    }

    @Override
    public String getFormula() {
        return "KI";
    }

    @Override
    public FoodItem clone() {
        return new IodinePill();
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        super.triggerSpecificReaction(eatenBy, gameData);
        eatenBy.setCharacter(new LimitedRadiationProtection(eatenBy.getCharacter(), gameData.getRound(), MyRandom.nextInt(3)+2));
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A chemical compound which protects the thyroid gland. Useful during radiation emergencies.";
    }
}
