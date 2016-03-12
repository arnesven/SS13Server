package model.characters.decorators;

import model.Actor;
import model.events.AsphyxiationDamage;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.events.Damager;

public class SpaceProtection extends CharacterDecorator {

	public SpaceProtection(GameCharacter chara) {
		super(chara, "Space protection");
	}
	
	@Override
	public String getPublicName() {
		String res = "Space suit";
		if (isDead()) {
			res += " (dead)";
		}
		return res;
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager instanceof AsphyxiationDamage) {
			return;
		}
		super.beExposedTo(something, damager);
	}

}
