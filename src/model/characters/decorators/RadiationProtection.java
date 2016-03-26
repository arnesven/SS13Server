package model.characters.decorators;

import model.Actor;
import model.characters.GameCharacter;
import model.events.Damager;
import model.events.RadiationDamage;

public class RadiationProtection extends CharacterDecorator {

	public RadiationProtection(GameCharacter chara) {
		super(chara, "Radiation Protection");
	}
	
	@Override
	public String getPublicName() {
		String res = "Radiation suit";
		if (isDead()) {
			res += " (dead)";
		}
		return res;
	}
	
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager instanceof RadiationDamage) {
			if (this.getClient() != null) {
				this.getClient().addTolastTurnInfo("The rad suit protected you.");
			}
			return;
		}
		super.beExposedTo(something, damager);
	}


}
