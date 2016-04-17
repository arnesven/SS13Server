package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.Player;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.general.GameItem;
import model.items.weapons.Weapon;


public class ParasiteCharacter extends GameCharacter {

	public ParasiteCharacter() {
		super("Parasite", 0, -1.0);

	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return isDead()?'P':'p';
	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}
	
	@Override
	public String getPublicName() {
		return getBaseName();
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

	@Override
	public GameCharacter clone() {
		return new ParasiteCharacter();
	}
	
	@Override
	public boolean hasInventory() {
		return false;
	}
	
	@Override
	public boolean canUseObjects() {
		return false;
	}


    @Override
    public boolean getsAttackOfOpportunity(Weapon w) {
        return false;
    }
}
