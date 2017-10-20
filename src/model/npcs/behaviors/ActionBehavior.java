package model.npcs.behaviors;

import model.Actor;
import model.GameData;

public interface ActionBehavior extends Behavior {
	void act(Actor npc, GameData gameData);

}
