package model.npcs;

import model.characters.general.AlienCharacter;
import model.characters.general.GameCharacter;
import model.map.Room;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;

/**
 * Created by erini02 on 18/10/16.
 */
public class AlienNPC extends NPC {
    public AlienNPC(Room r) {
        super(new AlienCharacter(),
                new MeanderingMovement(0.75), new DoNothingBehavior(), r);
    }

    @Override
    public boolean hasInventory() {
        return false;
    }
}
