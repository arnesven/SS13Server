package model.npcs.animals;

import model.characters.general.AnimalCharacter;
import model.characters.general.WampaCharacter;
import model.map.rooms.IcePlanet;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class WampaNPC extends AnimalNPC {
    public WampaNPC(Room r) {
        super(new WampaCharacter(r.getID()),
                new MeanderingMovement(0.5),
                new AttackAllActorsNotSameClassBehavior(), r);
    }
}
