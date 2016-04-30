package model.objects.general;

import graphics.sprites.Sprite;
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
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("crate", "storage.png", 39);
    }
}