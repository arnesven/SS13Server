package model.objects.general;

import model.items.general.GameItem;
import model.map.rooms.Room;

public abstract class DispenserObject extends ContainerObject {

	public DispenserObject(String name, Room pos) {
		super(name, pos);
	}
	
	public void addItem(GameItem it) {
		getInventory().add(it);
	}

}
