package model.npcs;

import model.characters.general.ChimpCharacter;
import model.map.Room;
import model.npcs.behaviors.ChimpBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class ChimpNPC extends NPC implements Trainable {

	public ChimpNPC(Room r) {
		super(new ChimpCharacter(r), new MeanderingMovement(0.1), new ChimpBehavior(), r);
	}

	@Override
	public boolean hasInventory() {
		return true;
	}
	
	

}
