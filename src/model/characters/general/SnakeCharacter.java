package model.characters.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.characteractions.HissAction;
import model.items.weapons.Fangs;
import model.items.weapons.Weapon;

public class SnakeCharacter extends AnimalCharacter {

	public SnakeCharacter(int startRoom) {
		super("Snake", startRoom, -3.0);
	}

	private static Weapon fangs = new Fangs();

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (isDead()) {
            return new Sprite("snakedead", "alien.png", 26, this);
        }
        return new Sprite("snake", "alien.png", 15, this);
    }

    @Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		at.add(new HissAction());
	}
	
	@Override
	public Weapon getDefaultWeapon() {
		return fangs;
	}

	@Override
	public GameCharacter clone() {
		return new SnakeCharacter(this.getStartingRoom());
	}

}
