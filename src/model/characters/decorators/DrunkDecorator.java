package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.DrunkTimerEvent;
import util.MyRandom;

public class DrunkDecorator extends CharacterDecorator {

	private DrunkTimerEvent drunkTimer;
	
	public DrunkDecorator(GameCharacter chara, DrunkTimerEvent drunkTimer) {
		super(chara, "Drunk");
		this.drunkTimer = drunkTimer;
	}
	
	public DrunkTimerEvent getTimer() {
		return drunkTimer;
	}
	
	private double getColdProbability(int level) {
		if (level <= 0) return 0;
		switch(level) {
		case 1: return 0.2;
		case 2: return 0.4;
		case 3: return 0.65;
		case 4: return 0.75;
		case 5: return 0.78;
		default: return 0.80;
		}
	}
	
	public void setTimer(DrunkTimerEvent drunkTimer) {
		this.drunkTimer = drunkTimer;
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager instanceof ColdDamage &&
				MyRandom.nextDouble() < getColdProbability(drunkTimer.getLevel())) {
			this.getActor().addTolastTurnInfo("You are to drunk to feel the cold.");
			return;
		}
		super.beExposedTo(something, damager);
	}
	
}
