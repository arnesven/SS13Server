package model.objects.shipments;

import model.items.general.FragGrenade;
import model.items.suits.SecOffsVest;
import model.items.weapons.LaserPistol;
import model.characters.crew.HeadOfStaffCharacter;

public class MilitaryShipment extends Shipment {

	public MilitaryShipment() {
		super("Military", (new HeadOfStaffCharacter()).getSpeed());
		for (int i = 3; i > 0; --i) {
			this.add(new LaserPistol());
		}
		for (int i = 3; i > 0; --i) {
			this.add(new FragGrenade());
		}
        this.add(new SecOffsVest());
	}

	@Override
	public MilitaryShipment clone() {
		return new MilitaryShipment();
	}

}
