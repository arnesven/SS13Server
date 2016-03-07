package model.objects;

import model.items.GameItem;
import model.items.MedKit;

public class MedkitDispenser extends DispenserObject {

	public MedkitDispenser(int i) {
		super("Medical Storage", i);
	}

	@Override
	protected GameItem dispensedItem() {
		return new MedKit();
	}

}
