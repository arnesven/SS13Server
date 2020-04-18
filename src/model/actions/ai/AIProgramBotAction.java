package model.actions.ai;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.BotConsole;
import model.programs.BotProgram;

import java.util.List;

/**
 * Created by erini02 on 25/10/16.
 */
public class AIProgramBotAction extends Action {

    private final GameData gameData;
    private RobotNPC selectedBot;
    private BotProgram selectedProgram;

    public AIProgramBotAction(GameData gameData) {
        super("Reprogram Bot", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Reprogrammed Bot";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (Room r : gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
            for (Actor a : r.getActors()) {
                if (a instanceof RobotNPC && !a.isDead()) {
                    ActionOption subopt = new ActionOption(a.getBaseName());
                    addPrograms(gameData, subopt, whosAsking);

                    opt.addOption(subopt);
                }
            }
        }

        return opt;
    }

    private void addPrograms(GameData gameData, ActionOption subopt, Actor whosAsking) {
        try {
            for (BotProgram bp : gameData.findObjectOfType(BotConsole.class).getPrograms(gameData, whosAsking)) {
                subopt.addOption(bp.getName());
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        selectedProgram.loadInto(selectedBot, performingClient);
        performingClient.addTolastTurnInfo(selectedBot.getBaseName() + " was given new instructions.");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : gameData.getNPCs()) {
            if (a instanceof RobotNPC && !a.isDead() && args.get(0).equals(a.getBaseName())) {
                selectedBot = (RobotNPC)a;
            }
        }

        try {
            for (BotProgram bp : gameData.findObjectOfType(BotConsole.class).getPrograms(gameData, performingClient)) {
                if (args.get(1).equals(bp.getName())) {
                    selectedProgram = bp;
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("aiprogrambotabisprite", "interface_robot.png", 4, 2, null);
    }
}
