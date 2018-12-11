package model.npcs;

import model.characters.general.ParasiteCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.ParasiteBehavior;

public class ParasiteNPC extends NPC {

	public ParasiteNPC(Room startRoom) {
		super(new ParasiteCharacter(), new MeanderingMovement(0.75),
		   new ParasiteBehavior(), startRoom);
		this.setHealth(0.5);
		this.setMaxHealth(0.5);
	}
	
	@Override
	public boolean shouldBeCleanedUp() {
		return true;
	}

    @Override
    public NPC clone() {
        return new ParasiteNPC(getPosition());
    }

    @Override
	public boolean hasInventory() {
		return false;
	}

}
