package model.objects.shipments;

import model.items.Saxophone;
import model.items.foods.ApplePie;
import model.items.suits.FancyClothes;

public class PartyShipment extends Shipment {

	public PartyShipment() {
		super("Party", 1500);
		this.add(new ApplePie());
		this.add(new ApplePie());
		this.add(new FancyClothes());
		this.add(new FancyClothes());
		this.add(new Saxophone());
	}

	@Override
	public PartyShipment clone() {
		return new PartyShipment();
	}

}
