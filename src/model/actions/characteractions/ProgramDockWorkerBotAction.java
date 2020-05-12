package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.*;
import model.npcs.robots.DockWorkerBotCharacter;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDockWorkerBotAction extends Action {

    private final NPC bot;
    private final Map<String, LogisticalStep> innerBehaviors;
    private List<String> program;

    public ProgramDockWorkerBotAction(NPC dockWorkerBot, GameData gameData) {
        super("Program " + dockWorkerBot.getPublicName(), SensoryLevel.OPERATE_DEVICE);
        this.bot = dockWorkerBot;
        innerBehaviors = new HashMap<>();

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "tinkered with " + bot.getPublicName(whosAsking);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        fillMap(innerBehaviors, gameData, whosAsking);
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (String key : innerBehaviors.keySet()) {
            ActionOption innerOpt = new ActionOption(key);
            if (key.equals("Load Crate")) {
                innerOpt.addOption("Any");
                for (GameObject obj : bot.getPosition().getObjects()) {
                    if (obj instanceof CrateObject) {
                        ActionOption loadOpt = new ActionOption(obj.getPublicName(whosAsking));
                        addSimpleCommands(loadOpt);
                        innerOpt.addOption(loadOpt);
                    }
                }
            } else if (key.equals("Go To Room")) {
                for (Room r : gameData.getNonHiddenStationRooms()) {
                    ActionOption roomOpt = new ActionOption(r.getName());
                    addSimpleCommands(roomOpt);
                    innerOpt.addOption(roomOpt);
                }
            } else {
                addSimpleCommands(innerOpt);
            }

            opts.addOption(innerOpt);
        }

        return opts;
    }

    private void addSimpleCommands(ActionOption innerOpt) {
        innerOpt.addOption("Return To Me");
        ActionOption load = new ActionOption("Load Crate");
        load.addOption("Any");
        innerOpt.addOption(load);
        innerOpt.addOption("Unload Crate if Able");
        innerOpt.addOption("Follow Me");
    }

    private void fillMap(Map<String, LogisticalStep> innerBehaviors, GameData gameData, Actor whosAsking) {
        innerBehaviors.put("Return To Me",
                new LogisticalStep("Return To Me", new GoTowardsSpecificActorMovement(gameData, whosAsking),
                        new DockWorkerNothingBehavior()));
        innerBehaviors.put("Load Crate",
                new LogisticalStep("Load Crate", new StayBehavior(), new LoadCrateBehavior()));
        innerBehaviors.put("Unload Crate if Able",
                new LogisticalStep("Unload Crate if Able", new StayBehavior(), new UnloadCrateBehavior()));
        innerBehaviors.put("Go To Room",
                new LogisticalStep("Go To Room", null, new DockWorkerNothingBehavior()));
        innerBehaviors.put("Follow Me",
                new OngoingLogisticalStep("Follow Me", new DockWorkerFollowMeBehavior(whosAsking, gameData),
                        new ConcreteReadyForCommandsBehavior()));
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        fillMap(innerBehaviors, gameData, performingClient);
        LogisticsBehavior lb = new LogisticsBehavior(innerBehaviors, program, bot, gameData);
        performingClient.addTolastTurnInfo("You programmed the robot.");
        bot.setActionBehavior(lb);
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        this.program = new ArrayList<>();
        program.addAll(args.subList(0, args.size()-1));
    }
}
