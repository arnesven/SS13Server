package model.npcs;

import model.characters.ChimpCharacter;
import model.characters.GameCharacter;
import model.map.Room;

public class ChimpNPC extends NPC {

	public ChimpNPC(Room r) {
		super(new ChimpCharacter(r), new MeanderingMovement(0.9), new CrazyBehavior(), r);
		setMaxHealth(3.0);
		setHealth(3.0);
		
	}

	@Override
	public boolean hasInventory() {
		
		return true;
	}
	
	

}
