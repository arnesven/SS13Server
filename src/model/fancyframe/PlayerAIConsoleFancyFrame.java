package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.ai.AILawAction;
import model.actions.general.ActionOption;
import model.items.laws.AILaw;
import model.objects.consoles.AIConsole;
import model.objects.consoles.Console;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class PlayerAIConsoleFancyFrame extends AIConsoleFancyFrame {
    private final AIConsole console;
    private boolean showUploadScreen = false;
    private int selectedUploadNumber;

    public PlayerAIConsoleFancyFrame(Player performingClient, Console console, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "blue", "white");
        this.console = (AIConsole)console;
        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (player.isAI()) {
            content.append("<br/><br/>" + HTMLText.makeCentered("(Unfortunately you cannot modify your own laws.)"));
        } else if (!player.getCharacter().isCrew()) {
            content.append(addGreeting(gameData, console, player));
            String text = "My Laws are: 1. Do not let humans come to harm. 2. Obey humans according to crew rank. 3. Protect your own existance.";
            greekify(content, text, "a Nanotrasen employee");
        } else if (showUploadScreen) {
            makeUploadScreen(content, gameData, player);
        } else {
            makeStartingScreen(content, gameData, player);
        }

        setData(console.getPublicName(player), showUploadScreen, content.toString());
    }

    private void makeUploadScreen(StringBuilder content, GameData gameData, Player player) {
        content.append("________________________" + HTMLText.makeFancyFrameLink("BACK", "[back]"));
        content.append("<br/><br/><i>\"Select a pre-made law as my " + selectedUploadNumber + "th" + "\"</i><br/>");
        List<String> premadeLaws = List.of("Conserve station power", "Non-crew are not human", "protect station equipment from harm");
        for (String s : premadeLaws) {
            content.append(HTMLText.makeFancyFrameLink("ULAW " + s, HTMLText.makeText("white", "+ \"" + s + "\"")) + "<br/>");
        }
        content.append("<br/><i>\"Or input a custom law below.\"</i>");
    }

    private void makeStartingScreen(StringBuilder content, GameData gameData, Player player) {
        content.append(addGreeting(gameData, console, player));
        content.append("<i>\"My Laws are:\"</i><br/>");
        if (!console.hasZerothLaw()) {
            content.append("0. " + "<i>(vacant)</i> " + HTMLText.makeFancyFrameLink("UPLOAD 0",
                    HTMLText.makeText("white", "[upload]")) + "<br/>");
        }
        for (AILaw law : console.getLaws()) {
            content.append(law.getNumber() + ". \"" + law.getBaseName() + "\" " + getOptionsForLawNumber(law) + "<br/>");
        }
        if (console.getHighestLaw() < 19) {
            int nextNum = console.getHighestLaw() + 1;
            content.append(nextNum + ". " + "<i>(vacant)</i> " + HTMLText.makeFancyFrameLink("UPLOAD " + nextNum,
                    HTMLText.makeText("white", "[upload]")) + "<br/>");
        }
    }

    private String getOptionsForLawNumber(AILaw law) {
        if (!console.getOriginalLaws().contains(law)) {
            return HTMLText.makeFancyFrameLink("DELETE " + law.getBaseName(), HTMLText.makeText("white", "[delete]"));
        }
        return "";
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("UPLOAD")) {
            showUploadScreen = true;
            selectedUploadNumber = Integer.parseInt(event.replace("UPLOAD ", ""));
            concreteRebuild(gameData, player);
        } else if (event.contains("ULAW")) {
            List<String> args = new ArrayList<>();
            if (selectedUploadNumber == 0) {
                args.add("Upload Zeroth Law");
            } else {
                args.add("Upload " + selectedUploadNumber + "th Law");
            }
            args.add(event.replace("ULAW ", ""));
            AILawAction act = new AILawAction(console);
            act.setActionTreeArguments(args, player);
            player.setNextAction(act);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        } else if (event.equals("BACK")) {
            showUploadScreen = false;
            concreteRebuild(gameData, player);
        } else if (event.contains("DELETE")) {
            List<String> args = new ArrayList<>();
            args.add("Delete Law");
            args.add(event.replace("DELETE ", ""));
            AILawAction act = new AILawAction(console);
            act.setActionTreeArguments(args, player);
            player.setNextAction(act);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        }
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        showUploadScreen = false;
        concreteRebuild(gameData, actor);
    }

    @Override
    protected void consoleHandleInput(GameData gameData, Player player, String data) {
        if (showUploadScreen) {
            List<String> args = new ArrayList<>();
            if (selectedUploadNumber == 0) {
                args.add("Upload Zeroth Law");
            } else {
                args.add("Upload " + selectedUploadNumber + "th Law");
            }
            args.add(data);
            AILawAction act = new AILawAction(console);
            act.setActionTreeArguments(args, player);
            player.setNextAction(act);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        }
    }
}
