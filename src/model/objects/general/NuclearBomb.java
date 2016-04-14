package model.objects.general;

import model.Player;
import model.map.Room;

public class NuclearBomb extends GameObject {

	public NuclearBomb(Room position) {
		super("Nuclear Bomb", position);
	}
	
	@Override
	protected char getIcon(Player whosAsking) {
		return 'N';
	}

}
