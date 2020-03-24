package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.items.OperativeLocator;
import model.items.general.RemoteBomb;
import model.items.suits.OutFit;
import model.items.weapons.Revolver;
import model.map.rooms.Room;
import model.modes.GameMode;
import model.objects.general.ContainerObject;
import util.MyRandom;

public class OperativeStorage extends ContainerObject {
    public OperativeStorage(Room position) {
        super("Storage", position);
        this.getInventory().add(new OperativeLocator());
        this.getInventory().add(new Revolver());
        this.getInventory().add(new Revolver());
        getInventory().add(new OutFit(MyRandom.sample(GameMode.getAllCrew())));
        this.getInventory().add(new RemoteBomb());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("chemdispenser", "closet.png", 8, this);
    }
}
