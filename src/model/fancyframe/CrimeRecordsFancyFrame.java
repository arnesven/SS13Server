package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.objectactions.ReportCrimeAction;
import model.objects.consoles.CrimeRecordsConsole;
import util.HTMLText;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CrimeRecordsFancyFrame extends ConsoleFancyFrame {
    private final CrimeRecordsConsole console;
    private String reportDescr;
    private int reportDuration;
    private boolean onHistoryPage;
    private boolean onReportPage;
    private String reportedPerson;
    private String errorMessage = "";

    public CrimeRecordsFancyFrame(CrimeRecordsConsole console, Player performingClient, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "#ad402a", "Yellow");
        this.console = console;
        concreteRebuild(gameData, performingClient);
        onHistoryPage = false;
        onReportPage = false;
        reportDescr = null;
        reportDuration = -1;
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder data = new StringBuilder();

        if (onHistoryPage) {
            data.append("_______________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE SENTENCES", HTMLText.makeText("blue", "[sentences]")));
            data.append("<br/><br/><b>Report History:</b><br/>");
            if (!console.getReportsHistory().isEmpty()) {
                for (Map.Entry<Actor, List<Pair<String, Actor>>> entry : console.getReportsHistory().entrySet()) {
                    for (Pair<String, Actor> pair : entry.getValue()) {
                        data.append(pair.second.getBaseName() + " reported " + entry.getKey().getBaseName() + " for " + pair.first + "<br/>");
                    }
                }
                data.append("<br/>");
            } else {
                data.append("   * none *<br/>");
            }
        } else if (onReportPage) {
            data.append("__________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE BACK", HTMLText.makeText("blue", "[back]")));
            data.append("<br/>");
            data.append(errorMessage + "<br/>");
            data.append("Please enter crime description and sentence duration in the input field below and hit ENTER.");
            data.append(HTMLText.makeCentered("<i>Example: Murder-5</i>"));
            data.append("Then select who you want to report from the list below.<br/>");
            Action report = new ReportCrimeAction(console);
            for (ActionOption opt : report.getOptions(gameData, player).getSuboptions()) {
                String text = opt.getName();
                data.append(text + " ");
                if (reportDescr != null) {
                    data.append(HTMLText.makeFancyFrameLink("REPORT " + text, "[report]"));
                }
                data.append("<br/>");
            }
            data.append("<br/>" + HTMLText.makeText("Yellow", "serif", 2,"<i>If you cannot find the perpetrator in the list above. You may try to configure the security "  +
                    "system to recognize a new person by entering that persons name in the input field below and hitting ENTER.</i>"));

        } else {
            data.append("__________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE HISTORY", HTMLText.makeText("blue", "[history]")));
            data.append("<br/><br/><b>Current Sentences:</b><br/>");
            if (!console.getSentenceMap().isEmpty()) {
                for (Map.Entry<Actor, Integer> entry : console.getSentenceMap().entrySet()) {
                    data.append("  " + entry.getKey().getBaseName() + " : " + entry.getValue() + " rounds<br/>");
                }
                data.append("<br/>");
            } else {
                data.append("   * none *<br/>");
            }
            data.append("<br/>" + HTMLText.makeCentered(HTMLText.makeFancyFrameLink("REPORTPAGE", "[report a crime]")));
        }


        setData(console.getPublicName(player), onReportPage, HTMLText.makeText("Yellow", data.toString()));
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        reportedPerson = null;
        reportDescr = null;
        reportDuration = -1;
        onReportPage = false;
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("CHANGEPAGE HISTORY")) {
            this.onHistoryPage = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("CHANGEPAGE SENTENCES")) {
            this.onHistoryPage = false;
            concreteRebuild(gameData, player);
        } else if (event.contains("CHANGEPAGE BACK")) {
            this.onReportPage = false;
            concreteRebuild(gameData, player);
        } else if (event.contains("REPORTPAGE")) {
            this.onReportPage = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("REPORT")) {
            List<String> args = new ArrayList<>();
            Action a = new ReportCrimeAction(console);
            this.reportedPerson = event.replace("REPORT ", "");
            args.add(reportedPerson);
            args.add(reportDescr);
            args.add(reportDuration + "");
            a.setActionTreeArguments(args, player);
            player.setNextAction(a);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        }
    }

    @Override
    protected void consoleHandleInput(GameData gameData, Player player, String data) {
        super.consoleHandleInput(gameData, player, data);
        if (data.contains("-")) {
            String[] parts = data.split("-");
            Scanner scan = new Scanner(parts[1]);
            if (scan.hasNextInt()) {
                reportDescr = parts[0];
                reportDuration = scan.nextInt();
                concreteRebuild(gameData, player);
            }
        } else {
            boolean succ = console.addExtraBaddieByName(data, gameData);
            if (!succ) {
                errorMessage = "ERROR! No person \"" + data + "\" found on station.";
            }
            concreteRebuild(gameData, player);
        }
    }
}
