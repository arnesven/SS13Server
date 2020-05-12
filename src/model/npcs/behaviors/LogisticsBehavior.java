package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.npcs.NPC;
import util.Logger;
import util.MyStrings;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogisticsBehavior implements ActionBehavior {
    private final Map<String, LogisticalStep> innerBehaviors;
    private List<String> program;
    private LogisticalStep currentStep;

    public LogisticsBehavior(Map<String, LogisticalStep> innerBehaviors, List<String> program, NPC npc, GameData gameData) {
        this.innerBehaviors = innerBehaviors;
        this.program = program;
        parseStep(npc, gameData);
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        Logger.log("Logistical behavior, program is: " + MyStrings.join(program, ", "));
        currentStep.getActionBehavior().act(npc, gameData);
        stepIfDone(npc, gameData);

    }

    public void stepIfDone(Actor npc, GameData gameData) {
        if (currentStep.isDone(npc, gameData)) {
            Logger.log("Step is done, parsing next one");
            parseStep((NPC)npc, gameData);
        }
    }

    private void parseStep(NPC npc, GameData gameData) {
        if (program.isEmpty()) {
            Logger.log("Bot is done and ready for commands.");
            npc.setActionBehavior(new ConcreteReadyForCommandsBehavior());
            return;
        }
        Logger.log("Next program step is " + program.get(0));
        this.currentStep = innerBehaviors.get(program.get(0));
        Logger.log("Current step is "+ currentStep);
        program = step(program);
        if (currentStep.getName().equals("Go To Room")) {
            try {
                MovementBehavior mb = new GoTowardsSpecificRoomMovement(gameData, gameData.getRoom(program.get(0)));
                currentStep.setMoveBehavior(mb);
                npc.setMoveBehavior(mb);
                program = step(program);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        } else {
            npc.setMoveBehavior(currentStep.getMovementBehavior());
        }
        if (currentStep.getName().equals("Load Crate")) {
            if (!program.get(0).equals("Any")) {
                ((LoadCrateBehavior) currentStep.getActionBehavior()).setTarget(program.get(0));
            }
            program = step(program);
        }
    }

    private List<String> step(List<String> program) {
        List<String> rest = new ArrayList<>();
        for (int i = 1; i < program.size(); i++) {
            rest.add(program.get(i));
        }
        return rest;
    }

}
