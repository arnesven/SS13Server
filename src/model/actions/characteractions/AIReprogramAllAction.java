package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.BotConsole;
import model.programs.BotProgram;
import model.programs.BrainBotProgram;

import java.awt.*;
import java.util.List;

/**
 * Created by erini02 on 25/10/16.
 */
public class AIReprogramAllAction extends Action {
    private final GameData gameData;
    private BotProgram selectedProgram;

    public AIReprogramAllAction(GameData gameData) {
        super("Reprogram All Bots", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Reprogrammed all";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        try {
            for (BotProgram bp : gameData.findObjectOfType(BotConsole.class).getPrograms(gameData, whosAsking)) {
                if (! (bp instanceof BrainBotProgram)) {
                    opts.addOption(bp.getName());
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        for (Actor a : gameData.getNPCs()) {
            if (a instanceof RobotNPC && !a.isDead()) {
                selectedProgram.loadInto((RobotNPC)a, performingClient);
                performingClient.addTolastTurnInfo(a.getBaseName() + " was given new instructions.");
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        try {
            for (BotProgram bp : gameData.findObjectOfType(BotConsole.class).getPrograms(gameData, performingClient)) {
                if (bp.getName().equals(args.get(0))) {
                    selectedProgram = bp;
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }
}
