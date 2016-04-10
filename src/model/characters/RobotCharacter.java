package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.items.GameItem;
import model.items.weapons.BluntWeapon;
import model.items.weapons.Weapon;

public abstract class RobotCharacter extends GameCharacter {

	public RobotCharacter(String string, int i, double d) {
		super(string, i ,d);
		this.setMaxHealth(4.0);
		this.setHealth(4.0);
	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return 'T';
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<GameItem>();
	}
	
	@Override
	public boolean isHealable() {
		return false;
	}
	
	@Override
	public boolean isCrew() {
		return false;
	}

	@Override
	public Weapon getDefaultWeapon() {
		return new BluntWeapon("Steel Prod", 0.5){};
	}
	
	@Override
	public boolean hasInventory() {
		return false;
	}


}
