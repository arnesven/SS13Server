package model.characters;

import model.Player;
import model.items.weapons.Weapon;

public class SnakeCharacter extends AnimalCharacter {

	public SnakeCharacter(int startRoom) {
		super("Snake", startRoom, 0.0);
		this.removeSuit();
	}

	private static Weapon fangs = new Weapon("Fangs", 0.75, 0.5, false, 0.0);
	
	@Override
	public char getIcon(Player whosAsking) {
		return '(';
	}
	
	@Override
	public Weapon getDefaultWeapon() {
		return fangs;
	}

}
