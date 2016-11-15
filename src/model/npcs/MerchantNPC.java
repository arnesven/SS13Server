package model.npcs;

import model.characters.visitors.MerchantCharacter;
import model.items.suits.MerchantSuit;
import model.items.suits.SecOffsVest;
import model.map.Room;
import model.npcs.behaviors.MeanderingMovement;

/**
 * Created by erini02 on 15/11/16.
 */
public class MerchantNPC extends HumanNPC {
    public MerchantNPC(Room room) {
        super(new MerchantCharacter(room.getID()), room);
        putOnSuit(new SecOffsVest());
        putOnSuit(new MerchantSuit());
        setMoveBehavior(new MeanderingMovement(0.0));
    }
}
