package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;

import java.util.List;

public abstract class FreeAction extends Action {

    private final GameData gameData;
    private final Player player;
    private final Action oldAction;

    public FreeAction(String name, GameData gameData, Player performer) {
        super(name + " (Free Action)", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
        this.player = performer;
        oldAction = performer.getNextAction();
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "FreeAction"; // should not be needed
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // Should not be executed
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        doTheFreeAction(args, player, gameData);
        try {
            player.setNextAction(oldAction);
            gameData.setPlayerReady(gameData.getClidForPlayer(player), false);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    protected abstract void doTheFreeAction(List<String> args, Player p, GameData gameData);


    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
