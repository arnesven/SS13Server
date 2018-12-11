package model.npcs.animals;

import model.characters.general.BearCharacter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.MeanderingMovement;

/**
 * Created by erini02 on 03/09/16.
 */
public class BearNPC extends AnimalNPC{

    public BearNPC(Room r) {
        super(new BearCharacter(), new MeanderingMovement(0.5), new AttackAllActorsNotSameClassBehavior(), r);
        this.setHealth(3.0);
        this.setMaxHealth(3.0);
    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Override
    public NPC clone() {
        return new BearNPC(getPosition());
    }
}
