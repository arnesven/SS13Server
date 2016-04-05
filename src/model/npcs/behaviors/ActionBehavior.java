package model.npcs.behaviors;

import model.GameData;
import model.npcs.NPC;

public interface ActionBehavior {
	void act(NPC npc, GameData gameData);

}
