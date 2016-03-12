package model.events;

import model.Target;

public class NoPressureDamage extends AsphyxiationDamage  {

	public NoPressureDamage(Target t) {
		super(t);
	}

	@Override
	public double getDamage() {
		return 3.0;
	}
	
	@Override
	public String getText() {
		return "The nothingess of space sucks the life out of you!";
	}
}
