package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.events.ColdDamage;
import model.events.Damager;
import model.events.RadiationDamage;
import model.items.GameItem;
import model.items.weapons.Weapon;


public class ParasiteCharacter extends GameCharacter {

	public ParasiteCharacter() {
		super("Parasite", 0, -1.0);

	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}

	
	@Override
	public boolean isCrew() {
		return false;
	}
	
	@Override
	public boolean isHealable() {
		return false;
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		if (!(damager instanceof RadiationDamage) && !(damager instanceof ColdDamage)) {
			super.beExposedTo(something, damager);
		}
	}
	
	@Override
	public Weapon getDefaultWeapon() {
		return Weapon.CLAWS;
	}
	
}
