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
    public SitDownAtShuttleConsoleAction(GameData gameData, ShuttleControl shuttleControl) {
        super(gameData, shuttleControl);
    }


    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new ShuttleControlFancyFrame((ShuttleControl)console, gameData, performingClient);
    }
}
