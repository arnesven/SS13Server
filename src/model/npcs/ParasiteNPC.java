package model.npcs;

import model.characters.GameCharacter;
import model.characters.ParasiteCharacter;
import model.map.Room;

public class ParasiteNPC extends NPC {

	public ParasiteNPC(Room startRoom) {
		super(new ParasiteCharacter(), new MeanderingMovement(0.75),
		   new AttackIfPossibleBehavior(), startRoom);
		this.setHealth(0.5);
		this.setMaxHealth(0.5);
	}
	
	@Override
	public boolean shouldBeCleanedUp() {
		return true;
	}

}
