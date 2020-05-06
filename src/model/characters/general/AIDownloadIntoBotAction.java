package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.AITakenOverDecorator;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.StayBehavior;
import model.npcs.robots.RobotNPC;

import java.util.List;

/**
 * Created by erini02 on 25/10/16.
 */
public class AIDownloadIntoBotAction extends Action {
    private final GameData gameData;
    private RobotNPC selectedBot;

    public AIDownloadIntoBotAction(GameData gameData) {
        super("Download Into Bot", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Downloaded into bot";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Room r : gameData.getNonHiddenStationRooms()) {
            for (NPC npc : r.getNPCs()) {
                if (npc instanceof RobotNPC && !((RobotNPC) npc).isDead()) {
                    opts.addOption(npc.getBaseName());
                }
            }
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

        // Switch Rooms of ACTORS
        Room oldPos = selectedBot.getPosition();
        try {
            selectedBot.getPosition().removeNPC(selectedBot);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        performingClient.getPosition().addNPC(selectedBot);

        try {
            performingClient.getPosition().removeActor(performingClient);
            oldPos.addActor(performingClient);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        GameCharacter botChar = selectedBot.getCharacter();
        AITakenOverDecorator takenOver = new AITakenOverDecorator(selectedBot.getCharacter(),
                                        selectedBot.getMovementBehavior(), selectedBot.getActionBehavior(), selectedBot);

        selectedBot.setMoveBehavior(new StayBehavior());
        selectedBot.setActionBehavior(new DoNothingBehavior());
        selectedBot.setCharacter(performingClient.getCharacter());

        performingClient.setCharacter(takenOver);
        performingClient.addTolastTurnInfo("You downloaded into " + botChar.getBaseName() + ".");

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (NPC npc : gameData.getNPCs()) {
            if (npc instanceof RobotNPC && !((RobotNPC) npc).isDead()) {
                if (args.get(0).equals(npc.getBaseName())) {
                    selectedBot = (RobotNPC)npc;
                }
            }
        }

    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("aidownloadintobot", "interface_robot.png", 7, 4, null);
    }
}
