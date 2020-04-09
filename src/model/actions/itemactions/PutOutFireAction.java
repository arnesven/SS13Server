package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.events.NoSuchEventException;
import model.events.ambient.ElectricalFire;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.npcs.robots.RobotNPC;
import sounds.Sound;

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
        if (GameItem.hasAnItem(performingClient, new FireExtinguisher()) || isRobot(performingClient.getCharacter())) {
            try {
                ElectricalFire fire = null;
                try {
                    fire = FireExtinguisher.getFire(performingClient.getPosition());
                } catch (NoSuchEventException e) {
                    performingClient.addTolastTurnInfo("What, no fire? Your action failed.");
                    return;
                }
                fire.fix();
                if (fire.isRaging()) {
                    if (fireExtinguisher.getUsesRemaining() == 1) {
                        fire.setRaging(false);
                        performingClient.addTolastTurnInfo("Your fire extinguisher ran out! The fire is still burning though...");
                    } else {
                        fireExtinguisher.decrementLevel();
                        performingClient.addTolastTurnInfo("You put out the fire.");
                    }
                } else {
                    performingClient.addTolastTurnInfo("You put out the fire.");
                }
                fireExtinguisher.decrementLevel();

            } catch (NoSuchElementException nse) {
                performingClient.addTolastTurnInfo("No fire to put out.");
            }
        } else {
            performingClient.addTolastTurnInfo("What? The fire extinguisher is gone! Your action failed.");
        }
    }

    private boolean isRobot(GameCharacter character) {
        return character.checkInstance(((GameCharacter ch) -> ch instanceof RobotCharacter));
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("http://www.ida.liu.se/~erini02/ss13/spray.ogg");
    }
}
