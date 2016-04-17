package model.characters.decorators;

import model.Actor;
import model.Player;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
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
	public char getIcon(Player whosAsking) {
		return 'q';
	}
	
	@Override
	public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
		if (weapon instanceof Flamer) {
			performingClient.addTolastTurnInfo(this.getPublicName() + " is unaffected by your attack!");
			this.getActor().addTolastTurnInfo(performingClient.getPublicName() + 
					" tried to attack you with " + weapon.getPublicName(this.getActor()));
			return false;
		}
		return super.beAttackedBy(performingClient, weapon);
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (damager.getName().equals("fire")) {
			this.getActor().addTolastTurnInfo("Fire is raging around you.");
			return;
		}
		super.beExposedTo(something, damager);
	}

}
