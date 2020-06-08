package model.npcs;

import model.characters.general.EyeballAlienCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.*;

/**
 * Created by erini02 on 18/10/16.
 */
public class EyeballAlienNPC extends NPC {
    public EyeballAlienNPC(Room r) {
        super(new EyeballAlienCharacter(),
                new MeanderingMovement(0.75),
                new DoNothingBehavior(), // TODO: do something fun!
                r);
    }

    @Override
    public boolean hasInventory() {
        return true;
    }


    @Override
    public NPC clone() {
        return new EyeballAlienNPC(getPosition());
    }
}
