package model.npcs;

import model.characters.general.PirateCharacter;
import model.items.general.GameItem;
import model.items.suits.OxygenMask;
import model.items.suits.PirateOutfit;
import model.map.Room;
import model.npcs.behaviors.*;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateNPC extends AbstractPirateNPC {
    public PirateNPC(Room room, int num, Room targetRoom) {
        super(room, new PirateCharacter(num, room.getID()), targetRoom);
        putOnSuit(new PirateOutfit(num));
        putOnSuit(new OxygenMask());

    }
}
