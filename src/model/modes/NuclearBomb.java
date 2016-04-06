package model.modes;

import model.Player;
import model.map.Room;
import model.objects.GameObject;

public class NuclearBomb extends GameObject {

	public NuclearBomb(Room position) {
		super("Nuclear Bomb", position);
	}
	
	@Override
	protected char getIcon(Player whosAsking) {
		return 'N';
	}

}
