package model.actions.objectactions;

import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.CrimeRecordsFancyFrame;
import model.objects.consoles.Console;
import model.objects.consoles.CrimeRecordsConsole;

public class SitDownAtConsoleAtCrimeConsoleAction extends SitDownAtConsoleAction {
    public SitDownAtConsoleAtCrimeConsoleAction(GameData gameData, CrimeRecordsConsole crimeRecordsConsole) {
        super(gameData, crimeRecordsConsole);
    }

    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new CrimeRecordsFancyFrame((CrimeRecordsConsole) console, performingClient, gameData);
    }
}
