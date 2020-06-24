package model.npcs;

import model.characters.general.PirateCharacter;
import model.items.suits.OxygenMask;
import model.items.suits.PirateOutfit;
import model.items.suits.RolledDownCoverall;
import model.map.rooms.Room;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateNPC extends AbstractPirateNPC {
    public PirateNPC(Room room, int num, Room targetRoom, boolean isSuitedUp, RolledDownCoverall coverall) {
        super(room, new PirateCharacter(num, room.getID(), isSuitedUp), targetRoom);
        if (isSuitedUp) {
            putOnSuit(new PirateOutfit(num));
            putOnSuit(new OxygenMask());
        } else {
            putOnSuit(coverall);
            getCharacter().giveItem(new PirateOutfit(num), null);
            getCharacter().giveItem(new OxygenMask(), null);
        }
    }

    public PirateNPC(Room room, int num, Room targetRoom) {
        this(room, num, targetRoom, true, null);
    }
}
