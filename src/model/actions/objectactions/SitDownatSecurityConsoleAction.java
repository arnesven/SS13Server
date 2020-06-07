package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.SecurityCameraFancyFrame;
import model.objects.consoles.Console;

public class SitDownatSecurityConsoleAction extends SitDownAtConsoleAction {
    public SitDownatSecurityConsoleAction(GameData gameData, Console cl, Player p) {
        super(gameData, cl, p);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new SecurityCameraFancyFrame(console, gameData, performingClient);
    }
}
