package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.characteractions.HissAction;
import model.actions.characteractions.MeowingAction;
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
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		at.add(new MeowingAction());
		at.add(new HissAction());
	}
	
	
	@Override
	public Weapon getDefaultWeapon() {
		return Weapon.CLAWS;
	}

	@Override
	public GameCharacter clone() {
		return new CatCharacter();
	}
	
}
