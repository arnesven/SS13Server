package model.actions.fancyframeactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.fancyframe.PowerGeneratorFancyFrame;
import model.objects.consoles.GeneratorConsole;

import java.util.List;

public class SitDownAtPowerConsoleAction extends FancyFrameAction {
    private final GeneratorConsole console;
    private final GameData gameData;

    public SitDownAtPowerConsoleAction(GameData gameData, GeneratorConsole generatorConsole) {
        super("Sit Down at Console", SensoryLevel.PHYSICAL_ACTIVITY);
        this.console = generatorConsole;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "sat down at console";
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player && console.isFancyFrameVacant()) {
            console.setFancyFrameOccupied();
            ((Player) performingClient).setFancyFrame(new PowerGeneratorFancyFrame(console, ((Player) performingClient)));
        } else if (performingClient instanceof Player) {
            gameData.getChat().serverSay(console.getPublicName(performingClient) +
                    " is occupied right now, try again later.", (Player)performingClient);
        }
    }
}
