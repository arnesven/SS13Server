package model.npcs.animals;

import model.characters.general.GameCharacter;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;

/**
 * Created by erini02 on 03/09/16.
 */
public abstract class AnimalNPC extends NPC implements Trainable {
    public AnimalNPC(GameCharacter chara, MovementBehavior m, ActionBehavior a, Room r) {
        super(chara, m, a, r);
    }
}
