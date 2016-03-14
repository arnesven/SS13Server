package model.npcs;

import model.characters.GameCharacter;
import model.map.Room;

public class HumanNPC extends NPC {

	public HumanNPC(GameCharacter chara, Room room) {
		super(chara, new MeanderingHumanMovement(0.25), 
					 new DoNothingBehavior(), room);

	}

	@Override
	public boolean hasInventory() {
		return true;
	}

}
