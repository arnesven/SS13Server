package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.AIConsoleAction;
import model.items.NoSuchThingException;
import model.modes.GameMode;
import model.objects.consoles.AIConsole;
import model.objects.consoles.Console;
import util.HTMLText;
import util.Logger;
import util.Pair;

import java.util.List;

public class NPCAIConsoleFancyFrame extends AIConsoleFancyFrame {
    private final AIConsole console;
    private String selectedOption;
    private boolean showLocateScreen = false;
    private String selectedJob;

    public NPCAIConsoleFancyFrame(Player player, Console console, GameData gameData) {
        super(player.getFancyFrame(), console, gameData, "blue", "white");

        this.console = (AIConsole) console;
        concreteRebuild(gameData, player);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (console.isShutDown()) {
            content.append(HTMLText.makeColoredBackground("black",
                    HTMLText.makeText("gray", HTMLText.makeCentered("<br/><br/><i>(AI has been shut down)</i> "))));
        } else if (showLocateScreen) {
            content.append("________________________" + HTMLText.makeFancyFrameLink("BACK", "[back]"));
            content.append(HTMLText.makeCentered("<i>\"Who do you want me to locate?\"</i><br/>"));
            for (String job : GameMode.getAllCharsAsStrings()) {
                String bgColor = "blue";
                String fgColor = "white";
                if (job.equals(selectedJob)) {
                    bgColor = "gray";
                    fgColor = "yellow";
                }
                content.append(HTMLText.makeFancyFrameLink("LOC " + job, HTMLText.makeText(fgColor, bgColor, "+ " + job)) + "<br/>");
            }
        } else {
            content.append(super.addGreeting(gameData, console, player));

            List<Pair<String, String>> options = List.of(new Pair("CHECKALARMS", "Check Alarms"), new Pair("LOCATE", "Locate Crewmember"), new Pair("SHUTDOWN", "Shut Down AI"));
            for (Pair<String, String> p : options) {
                String bgColor = "blue";
                String fgColor = "white";
                if (p.first.equals(selectedOption)) {
                    bgColor = "gray";
                    fgColor = "yellow";
                }
                content.append(HTMLText.makeFancyFrameLink(p.first, HTMLText.makeText(fgColor, bgColor, "+ " + p.second)) + "<br/>");

            }

        }

        setData(console.getName(), false, content.toString());
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        Logger.log("AI Console fancy frame got event: " + event);
        if (event.equals("CHECKALARMS") || event.equals("SHUTDOWN")) {
            List<String> args;
            if (event.equals("CHECKALARMS")) {
                args = List.of("Check Alarms");
            } else {
                args = List.of("Shut Down AI");
            }
            AIConsoleAction aicons = new AIConsoleAction(console);
            aicons.setActionTreeArguments(args, player);
            player.setNextAction(aicons);
            readyThePlayer(gameData, player);
            selectedOption = event;
            concreteRebuild(gameData, player);
        } else if (event.equals("LOCATE")) {
            showLocateScreen = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("LOC ")) {
            selectedJob = event.replace("LOC ", "");
            List<String> args = List.of("Locate Crew Member", selectedJob);
            AIConsoleAction aicons = new AIConsoleAction(console);
            aicons.setActionTreeArguments(args, player);
            player.setNextAction(aicons);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        } else if (event.equals("BACK")) {
            showLocateScreen = false;
            concreteRebuild(gameData, player);
        }
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        super.doAtEndOfTurn(gameData, actor);
        if (showLocateScreen) {
            showLocateScreen = false;
            concreteRebuild(gameData, actor);
        }
        selectedJob = null;
        selectedOption = null;
    }
}
