package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.LarcenistCharacter;
import model.items.suits.GlovesItem;
import model.items.suits.SuitItem;

public class LarcenyGloves extends GlovesItem {

	public LarcenyGloves() {
		super("Larceny Gloves", 0.1, 299);
	}
	
	

	@Override
	public LarcenyGloves clone() {
		return new LarcenyGloves();
	}

	@Override
	protected Sprite getWornSprite(Actor whosAsking) {
		return new Sprite("larcenyglovesworn", "hands.png", 0, 3, this);
	}


    @Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new LarcenistCharacter(actionPerformer.getCharacter()));
	}



	@Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof LarcenistCharacter;
			}
		});
	}



	@Override
	public boolean permitsOver() {
		return true;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("larcenygloves", "gloves.png", 0, this);
    }

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "Enables the wearer to steal items from another character!" +
				" Stealing has a base success rate of 85% (will not alert victim). " +
				"However, there is only a 10% chance of success if the target is watching you. " +
				"Exposed characters (pinned, stunned or handcuffed) can always stolen from with 100% chance.";
	}
}
