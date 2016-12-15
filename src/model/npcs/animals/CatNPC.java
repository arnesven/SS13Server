package model.npcs.animals;

import model.characters.general.CatCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MeowOrHissBehavior;

public class CatNPC extends AnimalNPC {

	public CatNPC(Room startingRoom) {
		super(new CatCharacter(), new MeanderingMovement(0.5),
		  new MeowOrHissBehavior(0.5), startingRoom); 
	}

	@Override
	public boolean hasInventory() {
		return false;
	}
	
	

}
