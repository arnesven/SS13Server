package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.CoolDecorator;
import model.characters.decorators.InstanceChecker;

public class SunGlasses extends HeadGear {

	public SunGlasses() {
		super("Sun Glasses", 0.1, 19);
	}

	@Override
	public SunGlasses clone() {
		return new SunGlasses();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("sunglasses", "glasses.png", 1, this);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("sunglassesworn", "eyes.png", 4, this);
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new CoolDecorator(actionPerformer.getCharacter(), this));
		for (Actor a : actionPerformer.getPosition().getActors()) {
			if (a != actionPerformer) {
				a.addTolastTurnInfo("The " + actionPerformer.getPublicName() + " is a cool dude" + 
							(actionPerformer.getCharacter().getGender().equals("woman")?"ss":"") + "!");
			}
		}
	
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof CoolDecorator;
			}
		});
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "A pair of stylish sunglasses. Cool people wear these.";
	}
}
