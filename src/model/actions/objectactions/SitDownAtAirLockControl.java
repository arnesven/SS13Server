package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.fancyframe.AirLockControlFancyFrame;
import model.fancyframe.ConsoleFancyFrame;
import model.objects.consoles.AirLockConsole;
import model.objects.consoles.Console;

public class SitDownAtAirLockControl extends SitDownAtConsoleAction {
    public SitDownAtAirLockControl(GameData gameData, AirLockConsole airLockControl, Player p) {
        super(gameData, airLockControl, p);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new AirLockControlFancyFrame(performingClient, console, gameData, "blue", "white");
    }
}
