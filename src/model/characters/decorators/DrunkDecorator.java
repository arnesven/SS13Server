package model.characters.decorators;

import model.characters.GameCharacter;
import model.events.DrunkTimerEvent;

public class DrunkDecorator extends AttributeChangeDecorator {

	private DrunkTimerEvent timer;
	
	public DrunkDecorator(GameCharacter chara, DrunkTimerEvent timer) {
		super(chara, "Drunk", false);
		this.timer = timer;
	}
	
	public void soberUp() {
		timer.setCounter(0);
	}
	
	public void addDrunkness(int addition) {
		timer.setCounter(timer.getCounter() + addition);
	}
}
