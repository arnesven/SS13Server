package model.npcs;

import model.characters.general.MutatedTomatoCharacter;
import model.map.rooms.Room;
import model.npcs.animals.AnimalNPC;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.MeanderingMovement;

import java.util.List;

/**
 * Created by erini02 on 27/11/16.
 */
public class TomatoNPC extends AnimalNPC {
    public TomatoNPC(Room r) {
        super(new MutatedTomatoCharacter(r.getID()),
                new MeanderingMovement(0.75),
                new AttackAllActorsButNotTheseClasses(List.of(MutatedTomatoCharacter.class)), r);
    }

    @Override
    public boolean shouldBeCleanedUp() {
        return true;
    }

    @Override
    public NPC clone() {
        return new TomatoNPC(getPosition());
    }
}
