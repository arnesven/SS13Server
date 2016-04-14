package model.objects.general;

import model.Player;
import model.items.general.GameItem;
import model.map.Room;
import model.objects.shipments.Shipment;

public class CrateObject extends ContainerObject {

	
	public CrateObject(Room position, Shipment ship) {
		super(ship.getName() + " Crate", position);
		for (GameItem it : ship) {
			getInventory().add(it);
		}
	}

	@Override
	protected char getIcon(Player whosAsking) {
		return '#';
	}
}
