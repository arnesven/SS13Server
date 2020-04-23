package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.fancyframe.PlayerAIConsoleFancyFrame;
import model.fancyframe.ConsoleFancyFrame;
import model.objects.consoles.AIConsole;
import model.objects.consoles.Console;

public class SitDownAtAIConsoleAction extends SitDownAtConsoleAction {
    public SitDownAtAIConsoleAction(GameData gameData, AIConsole aiConsole) {
        super(gameData, aiConsole);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new PlayerAIConsoleFancyFrame(performingClient, console, gameData);
    }
}
