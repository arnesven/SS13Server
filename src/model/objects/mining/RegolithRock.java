package model.objects.mining;

import model.items.mining.OreShard;
import model.items.mining.RegolithShard;
import model.map.rooms.Asteroid;
import model.map.rooms.Room;
import model.objects.general.GameObject;

/**
 * Created by erini02 on 17/09/17.
 */
public class RegolithRock extends RockObject {
    public RegolithRock(Room asteroid) {
        super("Regolith Rock", asteroid);
    }

    @Override
    protected OreShard getOre() {
        return new RegolithShard();
    }
}
