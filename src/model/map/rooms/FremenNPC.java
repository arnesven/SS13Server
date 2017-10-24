package model.map.rooms;

import model.characters.special.FremenCharacter;
import model.items.suits.MarshalOutfit;
import model.items.suits.StillSuit;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;

public class FremenNPC extends NPC {
    public FremenNPC(DesertPlanet desertPlanet) {
        super(new FremenCharacter(desertPlanet.getID()),
                new MeanderingMovement(0.5),
                new DoNothingBehavior(), desertPlanet);
        putOnSuit(new StillSuit());
    }
}
