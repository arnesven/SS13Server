package model.characters.decorators;

import model.Actor;
import model.Player;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;

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
	public char getIcon(Player whosAsking) {
		return 'Q';
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager instanceof RadiationDamage) {
			
			this.getActor().addTolastTurnInfo("The rad suit protected you.");
			
			return;
		}
		super.beExposedTo(something, damager);
	}


}
