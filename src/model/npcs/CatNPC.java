package model.npcs;

import model.actions.MeowingAction;
import model.characters.CatCharacter;
import model.characters.GameCharacter;
import model.map.Room;

public class CatNPC extends NPC {

	public CatNPC(Room startingRoom) {
		super(new CatCharacter(), new MeanderingMovement(0.5),
		  new SpontaneousAct(0.5, new MeowingAction()), startingRoom); 
		
		this.setHealth(1.0);
		this.setMaxHealth(1.0);
	}

}
