package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.RequisitionsFancyFrame;
import model.objects.consoles.Console;
import model.objects.consoles.RequisitionsConsole;

public class SitDownAtRequisitionsConsoleAction extends SitDownAtConsoleAction {
    public SitDownAtRequisitionsConsoleAction(GameData gameData, RequisitionsConsole requisitionsConsole) {
        super(gameData, requisitionsConsole);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new RequisitionsFancyFrame(performingClient, console, gameData);
    }
}
