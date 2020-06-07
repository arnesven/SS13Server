package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.UnderConstructionConsoleFancyFrame;
import model.objects.consoles.Console;
import model.objects.consoles.CrimeRecordsConsole;

public class GeneralSitDownAtConsoleAction extends SitDownAtConsoleAction {
    public GeneralSitDownAtConsoleAction(GameData gameData, Console console, Player p) {
        super(gameData, console, p);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new UnderConstructionConsoleFancyFrame(console, gameData, performingClient);
    }
}
