package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.items.GameItem;
import model.items.weapons.Weapon;


public class CatCharacter extends AnimalCharacter {

	public CatCharacter() {
		super("Cat", 20, -5.0);
	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return '&';
	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}
	
	
	
	@Override
	public Weapon getDefaultWeapon() {
		return Weapon.CLAWS;
	}
	
}
