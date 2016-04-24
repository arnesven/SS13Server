package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import graphics.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.characteractions.HissAction;
import model.actions.characteractions.MeowingAction;
import model.items.general.GameItem;
import model.items.weapons.Weapon;


public class CatCharacter extends AnimalCharacter {

	public CatCharacter() {
		super("Cat", 20, -5.0);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("cat", "animal.png", 3, 20, 32, 32);
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
