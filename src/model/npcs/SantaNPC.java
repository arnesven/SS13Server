package model.npcs;

import model.characters.general.SantaClauseCharacter;
import model.items.suits.SantaSuit;
import model.map.rooms.Room;
import model.npcs.behaviors.*;

/**
 * Created by erini02 on 22/11/16.
 */
public class SantaNPC extends NPC {
    public SantaNPC(Room r) {
        super(new SantaClauseCharacter(r.getID()),
                new MeanderingMovement(0.1),
                new DoNothingBehavior(), r);
        this.setActionBehavior(new GiveAwayGiftsBehavior((SantaClauseCharacter)getCharacter()));
        giveStartingItemsToSelf();
        putOnSuit(new SantaSuit());
    }
}
