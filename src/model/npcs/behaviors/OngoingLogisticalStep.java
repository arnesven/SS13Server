package model.npcs.behaviors;

import model.Actor;
import model.GameData;

public class OngoingLogisticalStep extends LogisticalStep {
    public OngoingLogisticalStep(String follow_me, DockWorkerFollowMeBehavior dockWorkerFollowMeBehavior,
                                 ConcreteReadyForCommandsBehavior concreteReadyForCommandsBehavior) {
        super(follow_me, dockWorkerFollowMeBehavior, concreteReadyForCommandsBehavior);
    }

    @Override
    public boolean isDone(Actor npc, GameData gameData) {
        return false;
    }
}
