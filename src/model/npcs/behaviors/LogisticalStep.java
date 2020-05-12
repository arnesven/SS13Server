package model.npcs.behaviors;


import model.Actor;
import model.GameData;
import model.npcs.NPC;
import util.Logger;

import java.io.Serializable;

public class LogisticalStep implements Serializable {
    private final String name;
    private ActionBehavior actionBehavior;
    private MovementBehavior movementBehavior;

    public LogisticalStep(String name, MovementBehavior movementBehavior, ActionBehavior actionBehavior) {
        this.name = name;
        this.movementBehavior = movementBehavior;
        this.actionBehavior = actionBehavior;
    }

    public MovementBehavior getMovementBehavior() {
        return movementBehavior;
    }

    public ActionBehavior getActionBehavior() {
        return actionBehavior;
    }

    public String getName() {
        return name;
    }

    public boolean isDone(Actor npc, GameData gameData) {
        if (movementBehavior == null) {
            Logger.log("movebhavior was false");
            return false;
        }
        if (movementBehavior instanceof GoTowardsRoomMovement) {
            Logger.log("Movebehavior is GoTowardsROomMovement");
            return ((GoTowardsRoomMovement) movementBehavior).isDone((NPC)npc, gameData);
        }
        return true;
    }

    public void setMoveBehavior(MovementBehavior mb) {
        movementBehavior = mb;
    }
}
