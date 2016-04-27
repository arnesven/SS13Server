package model.npcs;

import model.characters.general.GameCharacter;
import model.characters.general.PirateCharacter;
import model.items.general.GameItem;
import model.items.suits.PirateOutfit;
import model.map.Room;
import model.npcs.behaviors.*;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateNPC extends HumanNPC {
    public PirateNPC(Room room, int num, Room targetRoom) {
        super(new PirateCharacter(num, room.getID()), room);
        this.setActionBehavior(new PirateBehavior());
        this.setMoveBehavior(new MoveTowardsBehavior(targetRoom, new MeanderingHumanMovement(0.1),
                new AttackIfPossibleBehavior()));
        //this.takeOffSuit();
        putOnSuit(new PirateOutfit(num));
        for (GameItem it : getCharacter().getStartingItems()) {
            getCharacter().giveItem(it, null);
        }
    }
}
