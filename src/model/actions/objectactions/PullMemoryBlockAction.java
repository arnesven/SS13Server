package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.objects.ai.AIMemory;
import model.objects.consoles.AIConsole;

import java.util.List;

/**
 * Created by erini02 on 02/11/16.
 */
public class PullMemoryBlockAction extends Action {
    private final Player aiPlayer;
    private final GameCharacter aiCharacter;

    public PullMemoryBlockAction(AIMemory aiMemory, Player aiPlayer, GameCharacter aiCharacter) {
        super("Pull Memory (" + aiMemory.getBlocks() + " blocks left)", SensoryLevel.OPERATE_DEVICE);
        this.aiPlayer = aiPlayer;
        this.aiCharacter = aiCharacter;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with AI Memory Bank";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!aiCharacter.isDead()) {
            performingClient.addTolastTurnInfo("You pulled out memory block nr " + (4 - (int)aiCharacter.getHealth()));
            aiCharacter.setHealth(aiCharacter.getHealth() - 1.0);
            aiPlayer.addTolastTurnInfo("Someone is pulling your memory. You can feel your mind is going...");
            if (aiCharacter.isDead()) {
                aiCharacter.setKiller(performingClient);
                performingClient.addTolastTurnInfo("You shut down the AI! You're on your own now.");
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
