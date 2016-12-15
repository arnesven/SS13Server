package model.npcs;

import model.characters.general.GameCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;

/**
 * Created by erini02 on 03/05/16.
 */
public class ZombieNPC extends NPC {
    public ZombieNPC(GameCharacter chara,
                     MovementBehavior m,
                     ActionBehavior a, Room r) {
        super(chara, m, a, r);
    }

    @Override
    public boolean hasInventory() {
        return false;
    }
}
