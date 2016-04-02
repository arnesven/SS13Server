package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.items.GameItem;
import model.items.weapons.BluntWeapon;
import model.items.weapons.Weapon;

public class TarsCharacter extends GameCharacter {

	public TarsCharacter() {
		super("TARS", 0, 20.0);
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
}
