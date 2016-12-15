package model.npcs;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.MeanderingHumanMovement;
import model.npcs.behaviors.MoveTowardsBehavior;
import model.npcs.behaviors.PirateBehavior;

/**
 * Created by erini02 on 11/11/16.
 */
public class AbstractPirateNPC extends HumanNPC {

    public AbstractPirateNPC(Room room, GameCharacter pirateChar, Room targetRoom) {
        super(pirateChar, room);
        this.setActionBehavior(new PirateBehavior());
        this.setMoveBehavior(new MoveTowardsBehavior(targetRoom, new MeanderingHumanMovement(0.1),
                new AttackAllActorsNotSameClassBehavior()));
        for (GameItem it : getCharacter().getStartingItems()) {
            getCharacter().giveItem(it, null);
        }
    }
}
