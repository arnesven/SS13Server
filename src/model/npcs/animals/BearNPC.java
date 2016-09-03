package model.npcs.animals;

import model.characters.general.BearCharacter;
import model.characters.general.GameCharacter;
import model.map.Room;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.AttackIfPossibleBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;

/**
 * Created by erini02 on 03/09/16.
 */
public class BearNPC extends AnimalNPC{

    public BearNPC(Room r) {
        super(new BearCharacter(), new MeanderingMovement(0.5), new AttackIfPossibleBehavior(), r);
        this.setHealth(3.0);
        this.setMaxHealth(3.0);
    }

    @Override
    public boolean hasInventory() {
        return false;
    }
}
