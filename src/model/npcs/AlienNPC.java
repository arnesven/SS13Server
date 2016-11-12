package model.npcs;

import model.characters.general.AlienCharacter;
import model.characters.general.GameCharacter;
import model.map.Room;
import model.npcs.behaviors.*;

/**
 * Created by erini02 on 18/10/16.
 */
public class AlienNPC extends NPC {
    public AlienNPC(Room r) {
        super(new AlienCharacter(),
                new MeanderingMovement(0.75), new RandomActionBehavior(), r);
    }

    @Override
    public boolean hasInventory() {
        return true;
    }
}