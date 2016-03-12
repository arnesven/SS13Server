package model.characters.decorators;

import model.Actor;
import model.characters.GameCharacter;
import model.events.Damager;
import model.items.weapons.Flamer;
import model.items.weapons.Weapon;


public class FireProtection extends CharacterDecorator {

	public FireProtection(GameCharacter chara) {
		super(chara, "Fire Suit");
	}
	
	@Override
	public String getPublicName() {
		String res = "Fire suit";
		if (isDead()) {
			res += " (dead)";
		}
		return res;
	}
	
	@Override
	public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
		if (weapon instanceof Flamer) {
			performingClient.addTolastTurnInfo(this.getPublicName() + " is unaffected by your attack!");
			if (this.getClient() != null) {
				this.getClient().addTolastTurnInfo(performingClient.getPublicName() + 
						" tried to attack you with " + weapon.getName());;
			}
			return false;
		}
		return super.beAttackedBy(performingClient, weapon);
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager.getName().equals("fire")) {
			if (this.getClient() != null) {
				this.getClient().addTolastTurnInfo("Fire is raging around you.");
			}
			return;
		}
		super.beExposedTo(something, damager);
	}

}
