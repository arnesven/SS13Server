package model.objects.shipments;

import model.items.general.Grenade;
import model.items.suits.SecOffsVest;
import model.items.weapons.LaserPistol;
import model.characters.crew.HeadOfStaffCharacter;

public class MilitaryShipment extends Shipment {

	public MilitaryShipment() {
		super("Military", 8000, (new HeadOfStaffCharacter()).getSpeed());
		for (int i = 3; i > 0; --i) {
			this.add(new LaserPistol());
		}
		for (int i = 3; i > 0; --i) {
			this.add(new Grenade());
		}
        this.add(new SecOffsVest());
	}

	@Override
	public MilitaryShipment clone() {
		return new MilitaryShipment();
	}

}
