package model.objects.general;

import model.Player;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.map.Room;

public class ChemicalDispenser extends DispenserObject {

	public ChemicalDispenser(String name, int i, Room pos) {
		super(name, pos);
		this.addItem(new Chemicals());
		this.addItem(new Chemicals());
	}

	@Override
	protected char getIcon(Player whosAsking) {
		return 'u';
	}

}
