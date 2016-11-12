package model.npcs;

import model.characters.general.PirateCaptainCharacter;
import model.items.suits.OxygenMask;
import model.items.suits.PirateCaptainOutfit;
import model.map.Room;

/**
 * Created by erini02 on 11/11/16.
 */
public class PirateCaptainNPC extends AbstractPirateNPC {

    public PirateCaptainNPC(Room room, Room targetRoom) {
        super(room, new PirateCaptainCharacter(room.getID()), targetRoom);
        putOnSuit(new OxygenMask());
        putOnSuit(new PirateCaptainOutfit());

    }
}
