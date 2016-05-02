package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.BombItem;
import model.items.general.GameItem;
import model.items.general.Tools;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 02/05/16.
 */
public class DefuseBombAction extends Action {
    private static final double DETONATION_CHANCE = 0.1;


    public DefuseBombAction() {
        super("Defuse Bomb", SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Tinkered with bomb";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!GameItem.hasAnItem(performingClient, new Tools())) {
            performingClient.addTolastTurnInfo("What? The Tools are missing! Your action failed.");
            return;
        }

        for (GameItem it : performingClient.getPosition().getItems()) {
            if (it instanceof BombItem) {
                if (MyRandom.nextDouble() < DETONATION_CHANCE) {
                    performingClient.addTolastTurnInfo("You failed to defuse the bomb!");
                    ((BombItem) it).explode(gameData, performingClient);
                } else {
                    performingClient.addTolastTurnInfo("You successfully defused the bomb.");
                    ((BombItem) it).defuse(gameData);
                    performingClient.getPosition().getItems().remove(it);
                }
                return;
            }
        }

        performingClient.addTolastTurnInfo("What? The bomb wasn't there! Your action failed.");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
