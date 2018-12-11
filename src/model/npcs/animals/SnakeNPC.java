package model.npcs.animals;

import model.actions.characteractions.HissAction;
import model.characters.general.SnakeCharacter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.SpontaneousAct;
import model.npcs.robots.RobotNPC;

public class SnakeNPC extends AnimalNPC {

	public SnakeNPC(Room room) {
		super(new SnakeCharacter(room.getID()),
				new MeanderingMovement(0.1),
				new SpontaneousAct(0.5, new HissAction()),
				room);
	
	}

	@Override
	public boolean hasInventory() {
		return false;
	}


    @Override
    public boolean shouldBeCleanedUp() {
        return true;
    }

    @Override
    public NPC clone() {
        return new SnakeNPC(getPosition());
    }
}
