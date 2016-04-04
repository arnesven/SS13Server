package model.npcs;

import model.Actor;
import model.characters.GameCharacter;
import model.events.AsphyxiationDamage;
import model.events.ColdDamage;
import model.events.Damager;
import model.events.RadiationDamage;
import model.map.Room;
import model.objects.Repairable;

public abstract class RobotNPC extends NPC implements Repairable {

	public RobotNPC(GameCharacter chara, MovementBehavior m, ActionBehavior a,
			Room r) {
		super(chara, m, a, r);
		this.setHealth(4.0);
		this.setMaxHealth(4.0);
	}

	
	@Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public boolean isDamaged() {
		return getCharacter().getHealth() < this.getMaxHealth();
	}

	@Override
	public boolean isBroken() {
		return isDead();
	}
	
	@Override
	public boolean isHealable() {
		return false;
	}
	
	@Override
	public void beExposedTo(Actor performingClient, Damager damage) {
		if (damage instanceof AsphyxiationDamage || damage instanceof RadiationDamage || damage instanceof ColdDamage) {
			return;
		}
		super.beExposedTo(performingClient, damage);
	}

}
