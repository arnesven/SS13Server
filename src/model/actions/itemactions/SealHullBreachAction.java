package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.events.Event;
import model.events.ambient.HullBreach;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.npcs.robots.RobotNPC;

import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class SealHullBreachAction extends Action {

    public SealHullBreachAction() {
        super("Seal hull breach", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    public void setArguments(List<String> args, Actor p) {
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "sealed the breach";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (GameItem.hasAnItem(performingClient, new Tools()) || isRobot(performingClient.getCharacter())) {
            List<Event> evs = performingClient.getPosition().getEvents();
            for (Event e : evs) {
                if (e instanceof HullBreach) {
                    ((HullBreach) e).fix();
                    break;
                }
            }
            performingClient.addTolastTurnInfo("You sealed the breach.");
        } else {
            performingClient.addTolastTurnInfo("What? The tools are gone! Your action failed.");
        }
    }

    private boolean isRobot(GameCharacter character) {
        return character.checkInstance(((GameCharacter ch) -> ch instanceof RobotCharacter));
    }
}
