package model.npcs;

import model.characters.GameCharacter;
import model.characters.TarsCharacter;
import model.map.Room;
import model.objects.Repairable;

public class TARSNPC extends NPC implements Repairable {

	public TARSNPC(Room r) {
		super(new TarsCharacter(), 
				new MeanderingMovement(0.5), 
				new RandomSpeechBehavior("resources/TARS.TXT"), r);
		this.setMaxHealth(4.0);
		this.setHealth(4.0);
	}

	@Override
	public boolean hasInventory() {
		return true;
	}

	@Override
	public boolean isDamaged() {
		return getCharacter().getHealth() < this.getMaxHealth();
	}

	@Override
	public boolean isBroken() {
		return isDead();
	}

}
