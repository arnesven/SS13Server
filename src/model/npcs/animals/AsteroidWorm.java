package model.npcs.animals;

import model.map.rooms.Asteroid;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class AsteroidWorm extends AnimalNPC {
    public AsteroidWorm(Room r) {
        super(new AsteroidWormCharacter(r.getID()),
                new MeanderingMovement(0.0),
                new AttackAllActorsNotSameClassBehavior(), r);
    }


    @Override
    public NPC clone() {
        return new AsteroidWorm(getPosition());
    }
}
