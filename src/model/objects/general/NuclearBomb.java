package model.objects.general;

import graphics.Sprite;
import model.Player;
import model.map.Room;

public class NuclearBomb extends GameObject {

	public NuclearBomb(Room position) {
		super("Nuclear Bomb", position);
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("nuclearbomb", "stationobjs.png", 98);
    }
}
