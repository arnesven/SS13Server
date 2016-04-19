package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.NoSuchEventException;
import model.events.ambient.ElectricalFire;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by erini02 on 15/04/16.
 */
public class PutOutFireAction extends Action {

    private FireExtinguisher fireExtinguisher;

    public PutOutFireAction(FireExtinguisher fireExtinguisher) {
        super("Put out fire", new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
                SensoryLevel.AudioLevel.SAME_ROOM,
                SensoryLevel.OlfactoryLevel.WHIFF));
        this.fireExtinguisher = fireExtinguisher;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "put out the fire";
    }

    @Override
    public void setArguments(List<String> args, Actor p) {
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (GameItem.hasAnItem(performingClient, new FireExtinguisher())) {
            try {
                ElectricalFire fire = null;
                try {
                    fire = FireExtinguisher.getFire(performingClient.getPosition());
                } catch (NoSuchEventException e) {
                    performingClient.addTolastTurnInfo("What, no fire? Your action failed.");
                    return;
                }
                fire.fix();
                fireExtinguisher.decrementLevel();
                performingClient.addTolastTurnInfo("You put out the fire.");
            } catch (NoSuchElementException nse) {
                performingClient.addTolastTurnInfo("No fire to put out.");
            }
        } else {
            performingClient.addTolastTurnInfo("What the fire extinguisher is gone! Your action failed.");
        }
    }
}
