package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.ShuttleControlFancyFrame;
import model.objects.consoles.Console;
import model.objects.consoles.ShuttleControl;

import java.util.List;

public class SitDownAtShuttleConsoleAction extends SitDownAtConsoleAction {
    private final boolean hasAdvanced;

    public SitDownAtShuttleConsoleAction(GameData gameData, ShuttleControl shuttleControl, Player pl, boolean hasAdvanced) {
        super(gameData, shuttleControl, pl);
        this.hasAdvanced = hasAdvanced;
    }


    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new ShuttleControlFancyFrame((ShuttleControl)console, gameData, performingClient, hasAdvanced);
    }
}
