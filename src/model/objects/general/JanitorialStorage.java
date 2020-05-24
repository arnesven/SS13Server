package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.items.EmptyContainer;
import model.items.chemicals.Chemicals;
import model.items.general.Tools;
import model.map.rooms.JanitorialRoom;

public class JanitorialStorage extends DispenserObject {
    public JanitorialStorage(JanitorialRoom janitorialRoom) {
        super("Janitorial Storage", janitorialRoom);
        this.addItem(new EmptyContainer());
        this.addItem(Chemicals.createRandomChemicals());
        this.addItem(new Tools());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("janitorialstorage", "closet.png", 3, this);
    }

}
