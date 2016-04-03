package model.npcs;

import model.actions.characteractions.HissAction;
import model.characters.SnakeCharacter;
import model.map.Room;

public class SnakeNPC extends NPC implements Trainable {

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
	
	

}
