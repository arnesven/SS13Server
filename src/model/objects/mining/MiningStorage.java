package model.objects.mining;

import graphics.sprites.Sprite;
import model.Player;
import model.items.FlashLight;
import model.items.mining.MiningDrill;
import model.items.mining.MiningExplosives;
import model.items.suits.MinerSpaceSuit;
import model.map.rooms.MiningStationRoom;
import model.map.rooms.Room;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningStorage extends ContainerObject {
    public MiningStorage(Room room) {
        super("Mining Storage", room);
        getInventory().add(new MinerSpaceSuit());
        getInventory().add(new FlashLight());
        getInventory().add(new MiningDrill());
        getInventory().add(new MiningExplosives());

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("miningstorage", "closet.png", 11, this);
    }
}
