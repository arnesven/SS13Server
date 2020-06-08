package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.events.Event;
import model.events.ambient.HullBreach;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.items.tools.RepairTools;
import model.npcs.robots.RobotNPC;

import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class SealHullBreachAction extends Action implements QuickAction {

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
        if (GameItem.hasAnItem(performingClient, new RepairTools()) || isRobot(performingClient.getCharacter())) {
            List<Event> evs = performingClient.getPosition().getEvents();
            for (Event e : evs) {
                if (e instanceof HullBreach) {
                    ((HullBreach) e).fix();
                    break;
                }
            }
            performingClient.addTolastTurnInfo("You sealed the breach.");
            RepairTools rt = null;
            try {
                rt = GameItem.getItemFromActor(performingClient, new RepairTools());
                rt.makeHoldInHand(performingClient);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        } else {
            performingClient.addTolastTurnInfo("What? The tools are gone! Your action failed.");
        }
    }

    private boolean isRobot(GameCharacter character) {
        return character.checkInstance(((GameCharacter ch) -> ch instanceof RobotCharacter));
    }

    @Override
    public void performQuickAction(GameData gameData, Player performer) {
        execute(gameData, performer);
    }

    @Override
    public boolean isValidToExecute(GameData gameData, Player performer) {
        return true;
    }

    @Override
    public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
        return performer.getPosition().getClients();
    }
}
