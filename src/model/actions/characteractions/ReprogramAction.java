package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.npcs.RobotNPC;
import model.objects.consoles.BotConsole;
import model.programs.BotProgram;
import util.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by erini02 on 14/04/16.
 */
public class ReprogramAction extends Action {

    private RobotNPC selectedBot;
    private String progString;

    public ReprogramAction() {
        super("Load Program", SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption option = super.getOptions(gameData, whosAsking);
        for (Actor a : whosAsking.getPosition().getActors()) {
            if (a instanceof RobotNPC) {
                ActionOption sub = new ActionOption(a.getPublicName());
                try {
                    addProgramOpts(gameData, sub);
                } catch (NoSuchThingException e) {
                    throw new IllegalStateException("No bot console found on station.");
                }
                option.addOption(sub);
            }
        }
        return option;
    }

    private void addProgramOpts(GameData gameData, ActionOption sub) throws NoSuchThingException {
        List<BotProgram> programs = BotConsole.find(gameData).getPrograms(gameData);
        for (BotProgram bp : programs) {
            sub.addOption(bp.getName());
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Tinkered";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            if (selectedBot == null ||
                    selectedBot.getPosition() != performingClient.getPosition()) {
                performingClient.addTolastTurnInfo("What? Bot wasn't there! Your action failed.");
                return;
            }
            BotProgram selectedProgram = null;
            for (BotProgram bp : BotConsole.find(gameData).getPrograms(gameData)) {
                if (progString.equals(bp.getName())) {
                    selectedProgram = bp;
                }
            }
            selectedProgram.loadInto(selectedBot);
            performingClient.addTolastTurnInfo("You reprogrammed " + selectedBot.getPublicName() + ".");
        } catch (NoSuchThingException nste) {
            Logger.log("What? No bot console? No bot programs could be found!");
            performingClient.addTolastTurnInfo("No connection to bot console. Your action failed.");
        }
   }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : performingClient.getPosition().getActors()) {
            if (a instanceof RobotNPC) {
                if (args.get(0).equals(a.getPublicName())) {
                    selectedBot = (RobotNPC) a;
                }
            }
        }
        progString = args.get(1);
    }

}
