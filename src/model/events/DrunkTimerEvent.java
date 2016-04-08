package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.DrunkDecorator;
import util.MyRandom;

/**
 * @author chrho686
 * An event which counts how long an Actor should be drunk
 */
public class DrunkTimerEvent extends Event {

	private Actor target;
	private int counter;
	
	private Boolean firstTime;
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		firstTime = true;
		this.counter = counter;
	}

	public DrunkTimerEvent(Actor target, int countdown) {
		// TODO make target character drunk
		
		this.target = target;
		this.counter = countdown;
		
		this.firstTime = true;
		
		System.out.println(target.getBaseName() + " is drunk for " + countdown + " rounds.");
	}
	
	// TODO this should also actually do things
	@Override
	public void apply(GameData gameData) {
		if (target.isDead()) {
			counter = 0;
		} else {
			
			target.addTolastTurnInfo("You feel " + getDrunkness() + ".");
			
			if (!firstTime) {
				counter--;

				// TODO play with the chance
				// 25% chance that the character lose one extra drunkness level
				if (counter > 0 && MyRandom.nextDouble() < 0.25) {
					//counter--;
				}
			} else {
				firstTime = false;
			}
		}
		
		if (shouldBeRemoved(gameData)) {
			target.setCharacter(makeSober(target.getCharacter()));
		}
		
	}
	
	// TODO fix this mess, so that it actually removes
	// the drunk decorator wherever it may be in the line
	private GameCharacter makeSober(GameCharacter ch) {
		if (ch instanceof DrunkDecorator) {
			return ((CharacterDecorator)ch).getInner();
		}
		return ch;
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}
	
	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return counter <= 0;
	}
		
	public String getDrunkness() {
		switch(counter) {
		case 0: return "sober";
		case 1: return "tipsy";
		case 2: return "drunk";
		case 3: return "hammered";
		case 4: return "smashed";
		case 5: return "like a trainwreck";
		case 6: return "drunk beyond help";
		default: return "like you should be dead";
		}
	}	
}
