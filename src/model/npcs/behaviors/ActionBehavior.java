package model.npcs.behaviors;

import model.GameData;
import model.npcs.NPC;

public interface ActionBehavior extends Behavior {
	void act(NPC npc, GameData gameData);

}
