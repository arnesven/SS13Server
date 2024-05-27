package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import util.Logger;

public class PrisonerSuit extends TorsoSuit {

	private int number;

	public PrisonerSuit(int i) {
		super("Prison Clothes", 0.5, 49);
		this.number = i;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("prisonersuit", "uniforms.png", 2, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Clothing meant to be worn by an inmate.";
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("prisonersuitworn", "uniform.png", 12, 2, this);
    }

    @Override
	public PrisonerSuit clone() {
		return new PrisonerSuit(number);
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
        Logger.log(actionPerformer.getBaseName() + " put on the prisoner clothes.");
		actionPerformer.setCharacter(new PrisonerDecorator(actionPerformer.getCharacter(), this.number));
	}

	@Override
	public void beingTakenOff(Actor actionPerformer) {
        InstanceChecker check = new InstanceChecker() {

            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof PrisonerDecorator;
            }
        };

        if (actionPerformer.getCharacter().checkInstance(check)) {
            actionPerformer.removeInstance(check);
        }
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

}
