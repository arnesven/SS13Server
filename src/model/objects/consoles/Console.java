package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import model.objects.general.RemotelyOperateable;

public abstract class Console extends ElectricalMachinery implements RemotelyOperateable {
	
	public Console(String name, Room r) {
		super(name, r);
	}

	@Override
	public Sprite getSprite(Player whosAsking) {
		return new Sprite("console", "computer2.png", 10);
	}

}
