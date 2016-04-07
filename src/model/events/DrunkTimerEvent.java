package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import util.MyRandom;

/**
 * @author chrho686
 * An event which counts how long an Actor should be drunk
 */
public class DrunkTimerEvent extends Event {

	private Actor target;
	private int countdown;
	
	public DrunkTimerEvent(Actor target, int countdown) {
		// TODO make target character drunk
		
		this.target = target;
		this.countdown = countdown;
		
		System.out.println(target.getBaseName() + " is drunk for " + countdown + " rounds.");
	}
	
	// TODO this should also actually do things
	@Override
	public void apply(GameData gameData) {
		if (target.isDead()) {
			countdown = 0;
		} else {
			
			target.addTolastTurnInfo("You feel " + getDrunkness() + ".");
			
			countdown--;
			
			// TODO play with the chance
			// 25% chance that the character lose one extra drunkness level
			if (countdown > 0 && MyRandom.nextDouble() < 0.25) {
				countdown--;
			}
		}
		
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
		return countdown <= 0;
	}
	
	public String getDrunkness() {
		switch(countdown) {
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
