package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.characters.decorators.DrunkChecker;
import model.characters.decorators.DrunkDecorator;
import util.MyRandom;

/**
 * @author chrho686
 * An event which counts how long an Actor should be drunk
 */
public class DrunkTimerEvent extends Event {

	private Actor target;
	private int counter;
	
	private Boolean drinkRound;
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		drinkRound = true;
		this.counter = counter;
	}

	public DrunkTimerEvent(Actor target, int counter) {
		
		this.target = target;
		this.counter = counter;
		
		target.setCharacter(new DrunkDecorator(target.getCharacter(), this));		
		this.drinkRound = true;
	}
	
	@Override
	public void apply(GameData gameData) {
		if (target.isDead()) {
			counter = 0;
		} else {
			
			target.addTolastTurnInfo("You feel " + getDrunkness() + " (" + counter + ").");
			
			// only decrease drunkness level during rounds in which the actor hasn't consumed alcohol
			if (!drinkRound) {
				counter--;

				// TODO play with the chance
				// 25% chance that the character lose one extra drunkness level
				if (counter > 0 && MyRandom.nextDouble() < 0.25) {
					counter--;
				}
			} else {
				drinkRound = false;
			}
		}
		
		if (shouldBeRemoved(gameData)) {
			makeSober();
		}
		
	}
	
	private void makeSober() {
		target.removeInstance(new DrunkChecker());
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
