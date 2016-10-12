package model.npcs;

import model.characters.general.ParasiteCharacter;
import model.map.Room;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class ParasiteNPC extends NPC {

	public ParasiteNPC(Room startRoom) {
		super(new ParasiteCharacter(), new MeanderingMovement(0.75),
		   new AttackAllActorsNotSameClassBehavior(), startRoom);
		this.setHealth(0.5);
		this.setMaxHealth(0.5);
	}
	
	@Override
	public boolean shouldBeCleanedUp() {
		return true;
	}

	@Override
	public boolean hasInventory() {
		return false;
	}

}
