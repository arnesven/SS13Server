package model.objects.shipments;

import model.items.general.MedKit;
import model.items.general.Syringe;

public class MedicalShipment extends Shipment {

	public MedicalShipment() {
		super("Medical Supplies");
		this.add(new MedKit());
		this.add(new MedKit());
		this.add(new MedKit());
		this.add(new MedKit());
        this.add(new Syringe());
        this.add(new Syringe());
	}

	@Override
	public MedicalShipment clone() {
		return new MedicalShipment();
	}

}
