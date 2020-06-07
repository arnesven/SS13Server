package model.actions.fancyframeactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.objects.consoles.Console;

import java.util.List;

public abstract class SitDownAtConsoleAction extends FreeAction {
    private final Console console;
    private final GameData gameData;

    public SitDownAtConsoleAction(GameData gameData, Console console, Player p) {
        super("Use " + console.getName(), gameData, p);
        this.console = console;
        this.gameData = gameData;
    }


    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        if (console.isFancyFrameVacant()) {
            console.setFancyFrameOccupied();
            ConsoleFancyFrame ff = getNewFancyFrame(console, gameData, p);
            p.setFancyFrame(ff);
            p.setCharacter(new UsingGameObjectFancyFrameDecorator(p.getCharacter(), ff));
            p.refreshClientData();
        } else {
            gameData.getChat().serverInSay(console.getPublicName(p) +
                    " is occupied right now, try again later.", p);
        }
    }

    protected abstract ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient);

}
