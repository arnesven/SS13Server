package model.objects.general;

import graphics.Sprite;
import model.Player;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.general.Syringe;
import model.map.Room;

public class MedkitDispenser extends DispenserObject {

	public MedkitDispenser(int i, Room pos) {
		super("Medical Storage", pos);
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new Syringe());
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("medkitdispenser", "closet.png", 30);
    }
}
