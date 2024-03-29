package model.items.foods;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.ConsumeAction;
import model.characters.decorators.PoisonedDecorator;
import model.characters.general.GameCharacter;
import model.events.Experienceable;
import model.events.damage.PoisonDamage;
import model.items.general.GameItem;
import sounds.Sound;
import util.MyRandom;

public abstract class FoodItem extends GameItem {


	private double poisonChance;

	public FoodItem(String string, double weight, int cost) {
		super(string, weight, true, cost);
		poisonChance = 0.0;
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
		checkForPoisoning(eatenBy, gameData);
	}

	protected void checkForPoisoning(Actor eatenBy, GameData gameData) {
		if (MyRandom.nextDouble() < poisonChance &&
				!eatenBy.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PoisonedDecorator)) {
			eatenBy.addTolastTurnInfo("Wait... something doesn't taste quit right... You've been poisoned by the " + getPublicName(eatenBy) + "!");
		}
	}


	public boolean canBeCooked(GameData gameData, Actor performingClient) {
	    return true;
    }

	@Override
	public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
		return "<i>Consumable</i><br/>";
	}

	public boolean hasRealSound() {
		return false;
	}

	public Sound getRealSound() {
		return null;
	}

	protected Sound getCrunchySound() {
		return new Sound("eatfood");
	}

	@Override
	public Sound getPickUpSound() {
		return new Sound("wirecutter_pickup");
	}

	@Override
	public Sound getDropSound() {
		return new Sound("wirecutter_drop");
	}

	@Override
	public void exposeToRadiation(GameData gameData) {
		poisonChance += 0.01;
	}

	public void setPoisonChance(double poisonChance) {
		this.poisonChance = poisonChance;
	}
}
