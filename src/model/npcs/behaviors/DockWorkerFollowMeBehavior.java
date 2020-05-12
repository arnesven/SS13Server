package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.npcs.NPC;

public class DockWorkerFollowMeBehavior extends GoTowardsSpecificActorMovement implements ReadyForCommandsBehavior{
    public DockWorkerFollowMeBehavior(Actor whosAsking, GameData gameData) {
        super(gameData, whosAsking);
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        move((NPC)npc);
    }
}
