package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.HealWithMedKitAction;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.general.Syringe;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class MedkitDispenser extends DispenserObject {

	public MedkitDispenser(int i, Room pos) {
		super("Medical Storage", pos);
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new Syringe());
        this.addItem(new Antidote());
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("medkitdispenser", "closet.png", 30, this);
    }


}
