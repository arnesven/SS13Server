package model.npcs;

import model.characters.general.GameCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingHumanMovement;

public class HumanNPC extends NPC {

	public HumanNPC(GameCharacter chara, Room room) {
		super(chara, new MeanderingHumanMovement(0.25),
					 chara.getDefaultActionBehavior(),
					 room);

	}

	@Override
	public boolean hasInventory() {
		return true;
	}

}
