package model.npcs.animals;

import model.characters.general.WampaCharacter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.MeanderingMovement;

import java.util.ArrayList;

public class WampaNPC extends AnimalNPC {
    public WampaNPC(Room r) {
        super(new WampaCharacter(r.getID()),
                new MeanderingMovement(0.5),
                new AttackAllActorsButNotTheseClasses(new ArrayList<>()), r);
    }

    @Override
    public NPC clone() {
        return new WampaNPC(getPosition());
    }
}
