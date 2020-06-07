package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.PersonnelConsoleFancyFrame;
import model.objects.consoles.Console;
import model.objects.consoles.PersonnelConsole;

public class SitDownAtPersonnelConsoleAction extends SitDownAtConsoleAction {
    public SitDownAtPersonnelConsoleAction(GameData gameData, PersonnelConsole personnelConsole, Player p) {
        super(gameData, personnelConsole, p);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new PersonnelConsoleFancyFrame(performingClient, console, gameData);
    }
}
