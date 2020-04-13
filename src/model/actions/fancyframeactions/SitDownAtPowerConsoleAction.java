package model.actions.fancyframeactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.PowerGeneratorFancyFrame;
import model.fancyframe.UsingConsoleFancyFrameDecorator;
import model.objects.consoles.Console;
import model.objects.consoles.GeneratorConsole;

import java.util.List;

public class SitDownAtPowerConsoleAction extends SitDownAtConsoleAction {
    private final GeneratorConsole console;

    public SitDownAtPowerConsoleAction(GameData gameData, GeneratorConsole generatorConsole) {
        super(gameData, generatorConsole);
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
