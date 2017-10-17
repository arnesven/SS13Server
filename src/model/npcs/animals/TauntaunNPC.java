package model.npcs.animals;

import model.characters.general.TauntaunCharacter;
import model.map.rooms.IcePlanet;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class TauntaunNPC extends AnimalNPC {
    public TauntaunNPC(Room r) {
        super(new TauntaunCharacter(r.getID()),
                new MeanderingMovement(0.5), new DoNothingBehavior(), r);
    }
}
