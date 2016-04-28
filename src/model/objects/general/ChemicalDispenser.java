package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.items.general.Chemicals;
import model.map.Room;

public class ChemicalDispenser extends DispenserObject {

	public ChemicalDispenser(String name, int i, Room pos) {
		super(name, pos);
		this.addItem(new Chemicals());
		this.addItem(new Chemicals());
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("chemdispenser", "closet.png", 5);
    }
}
