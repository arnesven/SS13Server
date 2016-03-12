package model.objects;

import model.items.Chemicals;
import model.items.GameItem;

public class ChemicalDispenser extends DispenserObject {

	public ChemicalDispenser(String name, int i) {
		super(name);
		this.addItem(new Chemicals());
		this.addItem(new Chemicals());
	}

	@Override
	protected GameItem dispensedItem() {
		return new Chemicals();
	}

}
