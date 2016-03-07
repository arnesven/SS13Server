package model.objects;

import model.items.Chemicals;
import model.items.GameItem;

public class ChemicalDispenser extends DispenserObject {

	public ChemicalDispenser(String name, int i) {
		super(name, i);
	}

	@Override
	protected GameItem dispensedItem() {
		return new Chemicals();
	}

}
