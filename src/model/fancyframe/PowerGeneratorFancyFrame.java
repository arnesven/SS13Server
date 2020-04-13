package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.PowerLevelAction;
import model.items.NoSuchThingException;
import model.objects.consoles.GeneratorConsole;
import util.HTMLText;
import util.MyStrings;

import java.util.ArrayList;
import java.util.List;

public class PowerGeneratorFancyFrame extends ConsoleFancyFrame {
    private final GeneratorConsole console;

    public PowerGeneratorFancyFrame(GeneratorConsole console, Player player) {
        super(player.getFancyFrame(), console);
        this.console = console;

        String prios = MyStrings.join(console.getSource().getPrios(), ", ");
        prios = prios.substring(1, prios.length()-1);

        setData(console.getPublicName(player), false,
                HTMLText.makeColoredBackground("#736524",
                HTMLText.makeText("yellow",
                HTMLText.makeFancyFrameLink("LOGIN", "[log in]") + "" +
                HTMLText.makeCentered("<h3>Power Status</h3>") +
                        "Current Power Output: " + String.format("%.1f MW", console.getSource().getPowerLevel()) + "<br/>" +
                        HTMLText.makeCentered(
                                HTMLText.makeFancyFrameLink("SETPOWER Ongoing Increase", "Ongoing Incr.") + "  " +
                        HTMLText.makeFancyFrameLink("SETPOWER Ongoing Decrease", "Ongoing Decr.") + "<br/>" +
                        HTMLText.makeFancyFrameLink("SETPOWER Fixed Increase", "Fixed Incr.") + "  " +
                        HTMLText.makeFancyFrameLink("SETPOWER Fixed Decrease", "Fixed Decr.")) + "<br/>" +
                        "Power Priority: " + prios
                )));
    }

    @Override
    public void handleEvent(GameData gameData, Player pl, String event) {
        if (event.contains("DISMISS")) {
            leaveFancyFrame(gameData, pl);
        } else if (event.contains("SETPOWER")) {
            PowerLevelAction pla = new PowerLevelAction(console);
            List<String> args = new ArrayList<>();
            args.add(event.replace("SETPOWER ", ""));
            pla.setActionTreeArguments(args, pl);
            pl.setNextAction(pla);
            try {
                gameData.setPlayerReady(gameData.getClidForPlayer(pl), true);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
        super.handleEvent(gameData, pl , event);

    }


}
