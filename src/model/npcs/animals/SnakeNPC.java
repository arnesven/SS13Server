package model.npcs.animals;

import model.actions.characteractions.HissAction;
import model.characters.general.SnakeCharacter;
import model.map.Room;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.SpontaneousAct;

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
	
	

}
