package model.npcs.animals;

import model.characters.general.ChimpCharacter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.ChimpBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class ChimpNPC extends AnimalNPC {

	public ChimpNPC(Room r) {
		super(new ChimpCharacter(r), new MeanderingMovement(0.1), new ChimpBehavior(), r);
	}

	@Override
	public boolean hasInventory() {
		return true;
	}


    @Override
    public boolean shouldBeCleanedUp() {
        return true;
    }

    @Override
    public NPC clone() {
        return new ChimpNPC(getPosition());
    }
}
