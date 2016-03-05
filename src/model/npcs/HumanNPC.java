package model.npcs;

import model.characters.GameCharacter;
import model.map.Room;

public class HumanNPC extends NPC {

	public HumanNPC(GameCharacter chara, Room room) {
		super(chara, new MeanderingMovement(0.25), 
					 new DoNothingBehavior(), room);

	}

}
