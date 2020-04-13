package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.PowerLevelAction;
import model.actions.objectactions.PowerPrioAction;
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
        buildContent(player);
    }

    private void buildContent(Player player) {
        StringBuilder prios = new StringBuilder();
        for (String prio : console.getSource().getPrios()) {
            prios.append(HTMLText.makeFancyFrameLink("SETPRIO " + prio, "[" + prio + "] "));
        }

        String ongInc = "[Ongoing Increase]";
        String ongDec = "[Ongoing Decrease]";
        if (console.getSource().getOngoing() > 0.0) {
            ongInc = "<b>" + ongInc + "</b>";
        } else if (console.getSource().getOngoing() < 0.0) {
            ongDec = "<b>" + ongDec + "</b>";
        }

        setData(console.getPublicName(player), false,
                HTMLText.makeColoredBackground("#02558c",
                        HTMLText.makeText("white",
                                HTMLText.makeFancyFrameLink("LOGIN", "[log in]") + "" +
                                        HTMLText.makeCentered("<h3>Power Status</h3>") +
                                        "Current Power Output: " + String.format("%.1f MW", console.getSource().getPowerLevel()) + "<br/>" +
                                        HTMLText.makeCentered(
                                                HTMLText.makeFancyFrameLink("SETPOWER Ongoing Increase",  ongInc) + "  " +
                                                        HTMLText.makeFancyFrameLink("SETPOWER Ongoing Decrease", ongDec) + "<br/>" +
                                                        HTMLText.makeFancyFrameLink("SETPOWER Fixed Increase", "[Fixed Increase]") + "  " +
                                                        HTMLText.makeFancyFrameLink("SETPOWER Fixed Decrease", "[Fixed Decrease]")) + "<br/>" +
                                        "Power Priority:<br/>" + prios.toString()
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
            buildContent(pl);
            try {
                gameData.setPlayerReady(gameData.getClidForPlayer(pl), true);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }

        } else if (event.contains("SETPRIO")) {
            PowerPrioAction ppa = new PowerPrioAction(console);
            List<String> args = new ArrayList<>();
            args.add(event.replace("SETPRIO ", ""));
            ppa.setActionTreeArguments(args, pl);
            pl.setNextAction(ppa);
            buildContent(pl);
            try {
                gameData.setPlayerReady(gameData.getClidForPlayer(pl), true);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        } else {
            super.handleEvent(gameData, pl, event);
        }

    }


    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        buildContent(player);
    }
}
