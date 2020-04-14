package model.fancyframe;

import model.GameData;
import model.Player;
import model.objects.consoles.Console;
import model.objects.consoles.CrimeRecordsConsole;
import util.HTMLText;

public class UnderConstructionConsoleFancyFrame extends ConsoleFancyFrame {

    private final Console console;

    public UnderConstructionConsoleFancyFrame(Console console, GameData gameData, Player performingClient) {
        super(performingClient.getFancyFrame(), console, "#ad402a");
        this.console = console;
        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        setData(console.getPublicName(player), false, HTMLText.makeText("Yellow", HTMLText.makeCentered("<br/><i>(Under Construction)</i>")));
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {

    }
}
