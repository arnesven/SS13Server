package model.items.foods;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.ConsumeAction;
import model.items.general.GameItem;

public abstract class FoodItem extends GameItem {


	public FoodItem(String string, double weight, int cost) {
		super(string, weight, true, cost);
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("fooditem", "food.png", 2, this);
    }

    @Override
	public void addYourActions(GameData gameData,
                               ArrayList<Action> at,
                               Actor cl) {
	    super.addYourActions(gameData, at, cl);
		at.add(new ConsumeAction(this, cl));
	}
	
	public abstract double getFireRisk();
	public abstract FoodItem clone();
	protected abstract void triggerSpecificReaction(Actor eatenBy, GameData gameData);
	
	public void beEaten(Actor eatenBy, GameData gameData) {
        eatenBy.getCharacter().doWhenConsumeItem(this, gameData);
        if (eatenBy.getItems().contains(this)) {
            eatenBy.getItems().remove(this);
        } else if (eatenBy.getPosition().getItems().contains(this)) {
            eatenBy.getPosition().getItems().remove(this);
        }
		triggerSpecificReaction(eatenBy, gameData);
	}


    public boolean canBeCooked(GameData gameData, Actor performingClient) {
	    return true;
    }

	@Override
	public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
		return "<i>Consumable</i><br/>";
	}
}
