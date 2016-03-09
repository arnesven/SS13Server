package model.npcs;

import model.characters.GameCharacter;
import model.characters.ParasiteCharacter;
import model.items.Weapon;
import model.map.Room;

public class ParasiteNPC extends NPC {

	public ParasiteNPC(Room startRoom) {
		super(new ParasiteCharacter(), new MeanderingMovement(0.75),
		   new AttackIfPossibleBehavior(new Weapon("Claws", 0.75, 0.5, false)), startRoom);
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
