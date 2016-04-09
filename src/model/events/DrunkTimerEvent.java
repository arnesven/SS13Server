package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.DrunkChecker;
import model.characters.decorators.DrunkDecorator;
import model.characters.decorators.InstanceChecker;
import util.MyRandom;

/**
 * @author chrho686
 * An event which counts how long an Actor should be drunk
 */
public class DrunkTimerEvent extends Event {

	private Actor target;
	private int level;
	private InstanceChecker checker;
	
	private Boolean firstTime = true;
	private Boolean remove = false;
	
	public DrunkTimerEvent(Actor target, int level, InstanceChecker checker) {
		this.target = target;
		this.level = level;
		this.checker = checker;
		
		updatePreviousTimer();
	}
	
	@Override
	public void apply(GameData gameData) {
		if (remove) {
			return;
		}
		
		// wait for one round before decreasing drunk level
		if(!firstTime) {
			level--;
			
			// 25% chance to decrease another drunk level each round
			if(MyRandom.nextDouble() < 0.25) {
				level--;
			}
			
		} else {
			firstTime = false;
		}
			
		target.addTolastTurnInfo("You feel " + getDrunkLevelName() + ".");
		
		if (level <= 0 || target.isDead()) {
			// character is now sober so remove drunk decorator
			target.removeInstance(checker);
			markForRemoval();
		}
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return getDrunkLevelName();
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}
	
	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return remove;
	}
	
	public void markForRemoval() {
		remove = true;
	}
	
	/**
	 * @return if the target is drunk return its decorator, else return null
	 */
	private DrunkDecorator getDrunkDecorator() {
		GameCharacter ch = target.getCharacter();
		while(ch instanceof CharacterDecorator) {
			if (ch instanceof DrunkDecorator) {
				return ((DrunkDecorator)ch);
			}
			ch = ((CharacterDecorator)ch);
		}
		return null;
	}
	
	/**
	 * If the target is drunk, remove the old timer and update this one
	 */
	private void updatePreviousTimer() {
		DrunkDecorator decorator = getDrunkDecorator();
		
		// if the character wasn't drunk before then do nothing
		if(decorator == null) {
			System.out.println(target.getBaseName() + " became drunk.");
			return;
		}
		
		System.out.println(target.getBaseName() + " became even drunker!");
		
		// remove the previous timer and
		// take whatever was left and add it
		// to this timer instead
		level += decorator.getTimer().getDrunkLevel();
		decorator.getTimer().markForRemoval();
		
		decorator.setTimer(this);
	}
	
	private int getDrunkLevel() {
		return level;
	}
	
	private String getDrunkLevelName() {
		if (level <= 0) {
			return "sober";
		}
		switch(level) {
		case 1: return "buzzed";
		case 2: return "tipsy";
		case 3: return "drunk";
		case 4: return "hammered";
		case 5: return "smashed";
		case 6: return "blackout-drunk";
		default: return "out-of-control";
		}
	}
}
