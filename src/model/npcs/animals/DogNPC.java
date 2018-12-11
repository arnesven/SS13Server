package model.npcs.animals;

import model.characters.general.AnimalCharacter;
import model.characters.general.DogCharacter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.*;

/**
 * Created by erini02 on 03/09/16.
 */
public class DogNPC extends AnimalNPC {

    public DogNPC(Room r) {
        super(new DogCharacter(), new MeanderingMovement(0.5), new BarkingBehavior(), r);
    }


    @Override
    public boolean hasInventory() {
        return false;
    }

    @Override
    public NPC clone() {
        return new DogNPC(getPosition());
    }
}
