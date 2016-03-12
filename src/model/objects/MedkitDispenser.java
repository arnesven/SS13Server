package model.objects;

import model.items.Chemicals;
import model.items.GameItem;
import model.items.MedKit;

public class MedkitDispenser extends DispenserObject {

	public MedkitDispenser(int i) {
		super("Medical Storage");
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		
	}

	@Override
	protected GameItem dispensedItem() {
		return new MedKit();
	}

}
