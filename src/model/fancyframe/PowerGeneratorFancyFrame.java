package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.PowerLevelAction;
import model.actions.objectactions.PowerPrioAction;
import model.events.ambient.SimulatePower;
import model.objects.consoles.GeneratorConsole;
import model.objects.general.ElectricalMachinery;
import model.objects.general.PowerConsumer;
import model.objects.power.PowerSupply;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class PowerGeneratorFancyFrame extends ConsoleFancyFrame {
    private final GeneratorConsole console;
    private final SimulatePower powerSim;
    private boolean showAdvanced = false;

    public PowerGeneratorFancyFrame(GeneratorConsole console, GameData gameData, Player player) {
        super(player.getFancyFrame(), console, gameData, "#02558c", "white");
        this.console = console;
        this.powerSim = (SimulatePower)gameData.getGameMode().getEvents().get("simulate power");
        buildContent(gameData, player);
    }

    private void buildContent(GameData gameData, Player player) {
        if (showAdvanced) {
            StringBuilder content = new StringBuilder();
            content.append("======== POWER SOURCES ========<br/>");
            for (PowerSupply ps : powerSim.findPowerSources(gameData)) {
                if (ps.getPower() > 0.0) {
                    String eRemain = String.format(" %1.1f kWh", 1000*ps.getEnergy());
                    if (ps.getEnergy() >= 1000000.0) {
                        eRemain = " *Inf* MWh";
                    }
                    content.append(String.format("%d kW", (int)(ps.getPower()*1000)) + " " + ps.getName() + " " + eRemain + "<br/>");
                }
            }

            content.append("======= POWER CONSUMERS =======<br/>");

            for (PowerConsumer pc : powerSim.findConsumers(gameData)) {
                if (pc.getPowerConsumption() > 0.0) {
                    String textcolor = "yellow";
                    if (!((ElectricalMachinery) pc).isPowered()) {
                        textcolor = "black";
                    }
                    content.append(HTMLText.makeText(textcolor, pc.getPowerPriority() + " " +
                            String.format("%2.1f kW", pc.getPowerConsumption() * 1000) + " " +
                            ((ElectricalMachinery) pc).getBaseName() + " " + "<br/>"));
                }

            }

            setData(console.getPublicName(player), false,"________________________" +
                    HTMLText.makeFancyFrameLink("BACK", "[back]") + "<br/>" +
                    HTMLText.makeText("white", "#02558c", "Courier", 3, content.toString()));
        } else {
            StringBuilder prios = new StringBuilder();
            Logger.log("powersim is : " + powerSim);
            for (String prio : powerSim.getPrios()) {
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
            for (String s : powerSim.getStatusMessages(gameData)) {
                if (s.contains("->")) {
                    status.append(s + "<br>");
                }
            }
            String[] parts = powerSim.getStatusMessages(gameData).get(0).split("; ");
            String title = parts[0];
            String demand = parts[1];

            setData(console.getPublicName(player), false,
                    HTMLText.makeText("white", "______________________" + HTMLText.makeFancyFrameLink("ADVANCED", "[advanced]") + "<br/>" +
                            HTMLText.makeCentered("<b>" + title + "</b><br/>") +
                            "Current Power Demand: " + String.format("%.1f kW", 1000*console.getPowerSimulation().getPowerDemand(gameData)) + "<br/>" +
                            "   Current Power Output: " + HTMLText.makeText(getColorForPower(), String.format("%.1f kW", 1000*console.getPowerSimulation().getAvailablePower(gameData))) + "<br/>" +
                            HTMLText.makeCentered(
                                    HTMLText.makeFancyFrameLink("SETPOWER Ongoing Increase", ongInc) + "  " +
                                            HTMLText.makeFancyFrameLink("SETPOWER Ongoing Decrease", ongDec) + "<br/>" +
                                            HTMLText.makeFancyFrameLink("SETPOWER Fixed Increase", "[Fixed Increase]") + "  " +
                                            HTMLText.makeFancyFrameLink("SETPOWER Fixed Decrease", "[Fixed Decrease]")) + "<br/>" +
                            "Power Priority:<br/>" + prios.toString() + "<br/>" +
                            status.toString()
                    ));
        }
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
        } else if (event.contains("ADVANCED")) {
            this.showAdvanced = true;
            buildContent(gameData, pl);
        } else if (event.contains("BACK")) {
            this.showAdvanced = false;
            buildContent(gameData, pl);
        }

    }


    @Override
    public void concreteRebuild(GameData gameData, Player player) {
        buildContent(gameData, player);
    }
}
