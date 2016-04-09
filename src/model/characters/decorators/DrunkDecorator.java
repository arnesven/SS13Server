package model.characters.decorators;

import model.characters.GameCharacter;
import model.events.DrunkTimerEvent;

public class DrunkDecorator extends CharacterDecorator {

	private DrunkTimerEvent drunkTimer;
	
	public DrunkDecorator(GameCharacter chara, DrunkTimerEvent drunkTimer) {
		super(chara, "Drunk");
		this.drunkTimer = drunkTimer;
	}
	
	public DrunkTimerEvent getTimer() {
		return drunkTimer;
	}
	
	public void setTimer(DrunkTimerEvent drunkTimer) {
		this.drunkTimer = drunkTimer;
	}
	
}
