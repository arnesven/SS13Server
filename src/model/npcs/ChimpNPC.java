package model.npcs;

import model.characters.ChimpCharacter;
import model.characters.GameCharacter;
import model.map.Room;

public class ChimpNPC extends NPC {

	public ChimpNPC(Room r) {
		super(new ChimpCharacter(r), new MeanderingMovement(0.1), new CrazyBehavior(), r);
		setMaxHealth(2.0);
		setHealth(2.0);
		
	}

	@Override
	public boolean hasInventory() {
		return true;
	}
	
	

}
