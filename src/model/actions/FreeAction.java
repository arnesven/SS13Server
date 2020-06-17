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

    public FreeAction(String name, GameData gameData, Player performer) {
        super(name + " (0 AP)", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
        this.player = performer;
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
    protected final void setArguments(List<String> args, Actor performingClient) {
        doTheFreeAction(args, player, gameData);
        if (hasRealSound()) {
            player.getSoundQueue().add(getRealSound());
            if (this.getSense().sound == SensoryLevel.AudioLevel.SAME_ROOM || this.getSense().sound == SensoryLevel.AudioLevel.VERY_LOUD) {
                for (Player p : player.getPosition().getClients()) {
                    if (p != performingClient) {
                        p.getSoundQueue().add(getRealSound());
                    }
                }
            }
        }
    }

    protected abstract void doTheFreeAction(List<String> args, Player p, GameData gameData);


    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
