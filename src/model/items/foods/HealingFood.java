package model.items.foods;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.ChilledDecorator;
import model.characters.general.GameCharacter;

public abstract class HealingFood extends FoodItem {

    private Actor maker;

	public HealingFood(String string, double weight, Actor maker, int cost) {
        // CAREFUL! maker can be null!
		super(string, weight, cost);
        this.maker = maker;

    }

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        if (eatenBy != maker) {
            eatenBy.getAsTarget().addToHealth(1.0);
            eatenBy.addTolastTurnInfo("Mmm, tastes very good!");
        } else {
            eatenBy.getAsTarget().addToHealth(0.5);
            eatenBy.addTolastTurnInfo("Mmm, tastes good.");
        }
        if (eatenBy.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ChilledDecorator)) {
            eatenBy.removeInstance((GameCharacter gc) -> gc instanceof ChilledDecorator);
        }

	}

    public Actor getMaker() {
        return maker;
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return "<b>Made by: </b>" + (maker == performingClient?"You":"Someone else");
    }

    protected void setMaker(Actor maker) {
        this.maker = maker;
    }
}
