package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.NPCAIConsoleFancyFrame;
import model.objects.consoles.AIConsole;
import model.objects.consoles.Console;

public class SitDownAtNPCAIConsoleAction extends SitDownAtConsoleAction {
    public SitDownAtNPCAIConsoleAction(GameData gameData, AIConsole aiConsole) {
        super(gameData, aiConsole);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new NPCAIConsoleFancyFrame(performingClient, console, gameData);
    }
}
