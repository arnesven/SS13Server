package model.objects.shipments;

import model.items.general.Chemicals;
import model.items.weapons.Flamer;

public class ExterminationShipment extends Shipment {

	public ExterminationShipment() {
		super("Extermination");
		for (int i = 5; i > 0; --i) {
			this.add(Chemicals.createRandomChemicals());
		}
		for (int i = 3; i > 0; --i) {
			this.add(new Flamer());
		}
	}

	@Override
	public ExterminationShipment clone() {
		return new ExterminationShipment();
	}

}
