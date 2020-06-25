package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.ShuttleControlFancyFrame;
import model.objects.consoles.Console;
import model.objects.consoles.MiningShuttleControl;

public class SitDownAtShuttleConsoleAction extends SitDownAtConsoleAction {
    private final boolean hasAdvanced;

    public SitDownAtShuttleConsoleAction(GameData gameData, MiningShuttleControl shuttleControl, Player pl, boolean hasAdvanced) {
        super(gameData, shuttleControl, pl);
        this.hasAdvanced = hasAdvanced;
    }


    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new ShuttleControlFancyFrame((MiningShuttleControl)console, gameData, performingClient, hasAdvanced);
    }
}
