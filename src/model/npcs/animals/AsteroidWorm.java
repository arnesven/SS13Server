package model.npcs.animals;

import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.MeanderingMovement;

import java.util.List;

public class AsteroidWorm extends AnimalNPC {
    public AsteroidWorm(Room r) {
        super(new AsteroidWormCharacter(r.getID()),
                new MeanderingMovement(0.0),
                new AttackAllActorsButNotTheseClasses(List.of(AsteroidWormCharacter.class)), r);
    }


    @Override
    public NPC clone() {
        return new AsteroidWorm(getPosition());
    }
}
