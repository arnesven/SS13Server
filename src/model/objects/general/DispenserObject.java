package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.items.general.GameItem;
import model.map.rooms.Room;

public abstract class DispenserObject extends ContainerObject {

	public DispenserObject(String name, Room pos) {
		super(name, pos);
	}
	
	public void addItem(GameItem it) {
		getInventory().add(it);
	}

	@Override
	public Sprite getSprite(Player whosAsking) {
		return new Sprite("dispenserobj", "closet.png", 0, this);
	}
}
