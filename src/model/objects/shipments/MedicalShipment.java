package model.objects.shipments;

import model.items.Scalpel;
import model.items.general.Defibrilator;
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
        this.add(new Scalpel());
        this.add(new Defibrilator());
	}

	@Override
	public MedicalShipment clone() {
		return new MedicalShipment();
	}

}
