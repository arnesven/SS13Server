package model.npcs;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.npcs.behaviors.*;

/**
 * Created by erini02 on 11/11/16.
 */
public class AbstractPirateNPC extends HumanNPC {

    public AbstractPirateNPC(Room room, GameCharacter pirateChar, Room targetRoom) {
        super(pirateChar, room);
        this.setActionBehavior(new PirateBehavior());
        this.setMoveBehavior(new MoveTowardsBehavior(targetRoom, new MeanderingHumanMovement(0.1),
                new AttackNonPiratesBehavior()));
        for (GameItem it : getCharacter().getStartingItems()) {
            getCharacter().giveItem(it, null);
        }
    }
}
