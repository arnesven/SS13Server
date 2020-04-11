package model.objects.shipments;

import model.items.general.BigFireExtinguisher;
import model.items.general.FireExtinguisher;
import model.items.suits.FireSuit;

public class FireFighterShipment extends Shipment {

	public FireFighterShipment() {
		super("Fire Fighting");
		this.add(new FireExtinguisher());
		this.add(new FireExtinguisher());
		this.add(new BigFireExtinguisher());
		this.add(new FireSuit());
		this.add(new FireSuit());
	}

	@Override
	public FireFighterShipment clone() {
		return new FireFighterShipment();
	}

}
