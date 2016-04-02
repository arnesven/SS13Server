package model.objects;

import model.Player;
import model.items.Chemicals;
import model.items.GameItem;
import model.map.Room;

public class ChemicalDispenser extends DispenserObject {

	public ChemicalDispenser(String name, int i, Room pos) {
		super(name, pos);
		this.addItem(new Chemicals());
		this.addItem(new Chemicals());
	}

	@Override
	protected GameItem dispensedItem() {
		return new Chemicals();
	}
	
	@Override
	protected char getIcon(Player whosAsking) {
		return 'u';
	}

}
