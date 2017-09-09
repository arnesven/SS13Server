package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.items.general.Chemicals;
import model.items.general.FireExtinguisher;
import model.items.general.Tools;
import model.items.suits.FireSuit;
import model.map.rooms.Room;

public class ChemicalDispenser extends DispenserObject {

	public ChemicalDispenser(String name, int i, Room pos) {
		super(name, pos);
		this.addItem(Chemicals.createRandomChemicals());
		this.addItem(Chemicals.createRandomChemicals());
        this.addItem(new FireSuit());
        this.addItem(new FireExtinguisher());
        this.addItem(new Tools());
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("chemdispenser", "closet.png", 5);
    }
}
