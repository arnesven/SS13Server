package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.AIMemory;
import model.objects.consoles.AIConsole;

import java.util.List;

/**
 * Created by erini02 on 02/11/16.
 */
public class PullMemoryBlockAction extends Action {
    private final Player aiPlayer;

    public PullMemoryBlockAction(AIMemory aiMemory, Player aiPlayer) {
        super("Pull Memory (" + aiMemory.getBlocks() + " blocks left)", SensoryLevel.OPERATE_DEVICE);
        this.aiPlayer = aiPlayer;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with AI Memory Bank";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!aiPlayer.isDead()) {
            performingClient.addTolastTurnInfo("You pulled out memory block nr " + (3 - (int)aiPlayer.getHealth()));
            aiPlayer.addToHealth(-1.0);
            aiPlayer.addTolastTurnInfo("Someone is pulling your memory. You can feel your mind is going...");
            if (aiPlayer.isDead()) {
                aiPlayer.getCharacter().setKiller(performingClient);
                try {
                    gameData.findObjectOfType(AIConsole.class).shutDown(gameData);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
