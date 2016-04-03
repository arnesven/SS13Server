package model.npcs;

import model.characters.CatCharacter;
import model.map.Room;

public class CatNPC extends NPC implements Trainable {

	public CatNPC(Room startingRoom) {
		super(new CatCharacter(), new MeanderingMovement(0.5),
		  new MeowOrHissBehavior(0.5), startingRoom); 
	}

	@Override
	public boolean hasInventory() {
		return false;
	}
	
	

}
