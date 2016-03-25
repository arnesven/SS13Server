package model.objects;

import model.items.Chemicals;
import model.items.GameItem;
import model.items.MedKit;
import model.map.Room;

public class MedkitDispenser extends DispenserObject {

	public MedkitDispenser(int i, Room pos) {
		super("Medical Storage", pos);
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		
	}

	@Override
	protected GameItem dispensedItem() {
		return new MedKit();
	}

}
