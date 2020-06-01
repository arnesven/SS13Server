package model.npcs.robots;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.ProgramDockWorkerBotAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.npcs.NPC;
import model.npcs.behaviors.LogisticsBehavior;
import model.npcs.behaviors.ReadyForCommandsBehavior;

import java.util.ArrayList;

public class DockWorkerBotCharacter extends RobotCharacter {
    public DockWorkerBotCharacter(int number, int i) {
        super("DockWorker #" + number, i, 1.0);
    }

    @Override
    public Sprite getNormalSprite(Actor whosAsking) {
        return new Sprite("dockworkerbot", "aibots.png", 81, getActor());
    }

    @Override
    protected Sprite getBrokenSprite(Actor whosAsking) {
        return new Sprite("dockworkerbotbroken", "aibots.png", 89, getActor());
    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor otherActor, ArrayList<Action> at) {
        super.addActionsForActorsInRoom(gameData, otherActor, at);
        if (getActor() instanceof NPC) {
            if (((NPC) getActor()).getActionBehavior() instanceof ReadyForCommandsBehavior ||
                    ((NPC)getActor()).getMovementBehavior() instanceof ReadyForCommandsBehavior) {
                at.add(new ProgramDockWorkerBotAction((NPC)getActor(), gameData));
            }
        }
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);

        if (getActor() instanceof NPC) {
            if (((NPC) getActor()).getActionBehavior() instanceof LogisticsBehavior) {
                ((LogisticsBehavior) ((NPC) getActor()).getActionBehavior()).stepIfDone((NPC)getActor(), gameData);
            }
        }
    }
}
