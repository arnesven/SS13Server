package model.objects.shipments;

import model.items.MedKit;

public class MedicalShipment extends Shipment {

	public MedicalShipment() {
		super("Medical Supplies", 1000);
		this.add(new MedKit());
		this.add(new MedKit());
		this.add(new MedKit());
		this.add(new MedKit());
	}

	@Override
	public MedicalShipment clone() {
		return new MedicalShipment();
	}

}
