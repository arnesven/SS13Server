package model.npcs.behaviors;

import model.npcs.NPC;

public interface MovementBehavior extends Behavior {
	
	void move(NPC npc);

}
