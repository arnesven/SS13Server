package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MoveIfNoItemsMovement;
import model.npcs.behaviors.MovementBehavior;
import model.npcs.behaviors.RecycleItemsOnFloorBehavior;

import java.util.List;

public abstract class TurnOnRobotAction extends Action implements QuickAction {

    private final NPC bot;

    public TurnOnRobotAction(NPC npc) {
        super("Turn on " + npc.getBaseName(), SensoryLevel.OPERATE_DEVICE);
        this.bot = npc;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "turned on the " + bot.getBaseName();
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (bot.getPosition() == performingClient.getPosition()) {
            bot.setMoveBehavior(getNewMovementBehavior());
            bot.setActionBehavior(getActionBehavior());
            performingClient.addTolastTurnInfo("You turned the " + bot.getBaseName() + " on.");
        } else {
            performingClient.addTolastTurnInfo("Huh? " + bot.getBaseName() + " no longer there? " + failed(gameData, performingClient));
        }
    }

    protected abstract ActionBehavior getActionBehavior();

    protected abstract MovementBehavior getNewMovementBehavior();

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

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
