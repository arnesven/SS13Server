package model.npcs;

import model.characters.ChimpCharacter;
import model.characters.GameCharacter;
import model.map.Room;

public class ChimpNPC extends NPC implements Trainable {

	public ChimpNPC(Room r) {
		super(new ChimpCharacter(r), new MeanderingMovement(0.1), new ChimpBehavior(), r);
	}

	@Override
	public boolean hasInventory() {
		return true;
	}
	
	

}
