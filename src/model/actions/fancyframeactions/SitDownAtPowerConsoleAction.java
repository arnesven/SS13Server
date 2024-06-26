package model.actions.fancyframeactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.PowerGeneratorFancyFrame;
import model.objects.consoles.Console;
import model.objects.consoles.GeneratorConsole;

public class SitDownAtPowerConsoleAction extends SitDownAtConsoleAction {
    private final GeneratorConsole console;

    public SitDownAtPowerConsoleAction(GameData gameData, GeneratorConsole generatorConsole, Player p) {
        super(gameData, generatorConsole, p);
        this.console = generatorConsole;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "sat down at console";
    }


    @Override
    protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
        return new PowerGeneratorFancyFrame(this.console, gameData, performingClient);
    }
}
