package model.objects.consoles;

import model.Player;
import model.map.Room;
import model.objects.general.ElectricalMachinery;
import model.objects.general.RemotelyOperateable;

public abstract class Console extends ElectricalMachinery implements RemotelyOperateable {
	
	public Console(String name, Room r) {
		super(name, r);
	}

	@Override
	protected char getIcon(Player whosAsking) {
		return '@';
	}

}
