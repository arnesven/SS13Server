package model.objects;

import model.Player;
import model.map.Room;

public abstract class Console extends ElectricalMachinery implements RemotelyOperateable {
	
	public Console(String name, Room r) {
		super(name, r);
	}

	@Override
	protected char getIcon(Player whosAsking) {
		return '@';
	}

}
