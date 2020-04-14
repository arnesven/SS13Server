package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.objects.consoles.CrimeRecordsConsole;
import util.HTMLText;
import util.Pair;

import java.util.List;
import java.util.Map;

public class CrimeRecordsFancyFrame extends ConsoleFancyFrame {
    private final CrimeRecordsConsole console;
    private boolean onHistoryPage;

    public CrimeRecordsFancyFrame(CrimeRecordsConsole console, Player performingClient, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "#ad402a", "Yellow");
        this.console = console;
        concreteRebuild(gameData, performingClient);
        onHistoryPage = false;
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
                        data.append(entry.getKey().getBaseName() + " reported " + pair.second.getBaseName() + " for " + pair.first + "<br/>");
                    }
                }
                data.append("<br/>");
            } else {
                data.append("   * none *<br/>");
            }
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
        }


        setData(console.getPublicName(player), false, HTMLText.makeText("Yellow", data.toString()));
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("CHANGEPAGE HISTORY")) {
            this.onHistoryPage = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("CHANGEPAGE SENTENCES")) {
            this.onHistoryPage = false;
            concreteRebuild(gameData, player);
        }
    }
}
