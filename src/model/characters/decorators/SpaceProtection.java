package model.characters.decorators;

import model.Actor;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.SmokeInhalationDamage;

public class SpaceProtection extends CharacterDecorator {

	public SpaceProtection(GameCharacter chara) {
		super(chara, "Space protection");
	}
	
	@Override
	public String getPublicName() {
		String res = "Somebody in a space suit";
		return res;
	}


    @Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager instanceof AsphyxiationDamage ||
				damager instanceof ColdDamage || damager instanceof SmokeInhalationDamage) {
			return;
		}
		super.beExposedTo(something, damager);
	}

}
