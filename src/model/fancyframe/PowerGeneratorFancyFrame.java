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

    public PowerGeneratorFancyFrame(GeneratorConsole console, GameData gameData, Player player) {
        super(player.getFancyFrame(), console, gameData, "#02558c");
        this.console = console;
        buildContent(gameData, player);
    }

    private void buildContent(GameData gameData, Player player) {
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

        StringBuilder status = new StringBuilder();
        for (String s : console.getSource().getStatusMessages()) {
            if (s.contains("->")) {
                status.append(s + "<br>");
            }
        }
        String[] parts = console.getSource().getStatusMessages().get(0).split("; ");
        String title = parts[0];
        String demand = parts[1];

        setData(console.getPublicName(player), false,
                        HTMLText.makeText("white",
                                        HTMLText.makeCentered("<b>" + title + "</b><br/>(" + demand + ")<br/>") +
                                        "Current Power Output: " + HTMLText.makeText(getColorForPower(), String.format("%.1f MW", console.getSource().getPowerLevel())) + "<br/>" +
                                        HTMLText.makeCentered(
                                                HTMLText.makeFancyFrameLink("SETPOWER Ongoing Increase",  ongInc) + "  " +
                                                        HTMLText.makeFancyFrameLink("SETPOWER Ongoing Decrease", ongDec) + "<br/>" +
                                                        HTMLText.makeFancyFrameLink("SETPOWER Fixed Increase", "[Fixed Increase]") + "  " +
                                                        HTMLText.makeFancyFrameLink("SETPOWER Fixed Decrease", "[Fixed Decrease]")) + "<br/>" +
                                        "Power Priority:<br/>" + prios.toString() + "<br/>" +
                                        status.toString()
                        ));
    }

    private String getColorForPower() {
        double p = console.getSource().getPowerOutput();
        if (p == 0.0) {
            return "black";
        } else if (p < 0.3) {
            return "gray";
        } else if (p < 0.6) {
            return "blue";
        } else if (p < 0.8) {
            return "green";
        } else if (p < 1.1) {
            return "yellow";
        } else if (p < 1.2) {
            return "orange";
        } else if (p < 1.5) {
            return "red";
        } else {
            return "purple";
        }
    }

    @Override
    public void consoleHandleEvent(GameData gameData, Player pl, String event) {
        super.handleEvent(gameData, pl, event);
        if (event.contains("SETPOWER")) {
            PowerLevelAction pla = new PowerLevelAction(console);
            List<String> args = new ArrayList<>();
            args.add(event.replace("SETPOWER ", ""));
            pla.setActionTreeArguments(args, pl);
            pl.setNextAction(pla);
            buildContent(gameData, pl);
            readyThePlayer(gameData, pl);
        } else if (event.contains("SETPRIO")) {
            PowerPrioAction ppa = new PowerPrioAction(console);
            List<String> args = new ArrayList<>();
            args.add(event.replace("SETPRIO ", ""));
            ppa.setActionTreeArguments(args, pl);
            pl.setNextAction(ppa);
            buildContent(gameData, pl);
            readyThePlayer(gameData, pl);
        }

    }


    @Override
    public void concreteRebuild(GameData gameData, Player player) {
        buildContent(gameData, player);
    }
}
