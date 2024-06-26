package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.FancyClothesDecorator;
import model.characters.decorators.InstanceChecker;

public class FancyClothes extends TorsoSuit {

	
	public FancyClothes() {
		super("Fancy Clothes", 0.5, 129);
	}


	@Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("fancyclothes", "uniforms.png", 0, 6, this);
    }

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "A very pretty garment, for formal occasions.";
	}

	@Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("fancyclothesworn", "uniform2.png", 7, 23, this);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.addTolastTurnInfo("You got all dressed up!");
		actionPerformer.setCharacter(new FancyClothesDecorator(actionPerformer.getCharacter()));
	}


	@Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof FancyClothesDecorator;
			}
		});
		
	}
	
	@Override
	public FancyClothes clone() {
		return new FancyClothes();
	}

	@Override
	public boolean permitsOver() {
		return false;
	}

}
