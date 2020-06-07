package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.FTLControlFancyFrame;
import model.objects.consoles.Console;

public class SitDownAtFTLControl extends SitDownAtConsoleAction {
    public SitDownAtFTLControl(GameData gameData, Console console, Player p) {
        super(gameData, console, p);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new FTLControlFancyFrame(console, gameData, performingClient);
    }
}
