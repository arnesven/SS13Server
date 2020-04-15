package model.actions.fancyframeactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.PowerGeneratorFancyFrame;
import model.fancyframe.UsingConsoleFancyFrameDecorator;
import model.objects.consoles.Console;

import java.util.List;

public abstract class SitDownAtConsoleAction extends Action {
    private final Console console;
    private final GameData gameData;

    public SitDownAtConsoleAction(GameData gameData, Console console) {
        super("Sit Down at Console " + console.getName() + " (Free Action)", SensoryLevel.PHYSICAL_ACTIVITY);
        this.console = console;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "sat down at a console";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // Nothing happens, player did not select a real action...
    }

    @Override
    protected final void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player && console.isFancyFrameVacant()) {
            console.setFancyFrameOccupied();
            ConsoleFancyFrame ff = getNewFancyFrame(console, gameData, (Player)performingClient);
            ((Player) performingClient).setFancyFrame(ff);
            performingClient.setCharacter(new UsingConsoleFancyFrameDecorator(performingClient.getCharacter(), ff));
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        } else if (performingClient instanceof Player) {
            gameData.getChat().serverInSay(console.getPublicName(performingClient) +
                    " is occupied right now, try again later.", (Player)performingClient);
        }
    }

    protected abstract ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient);

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
