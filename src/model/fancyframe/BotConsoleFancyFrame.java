package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.ActionOption;
import model.actions.objectactions.ScanBrainAction;
import model.objects.consoles.BotConsole;
import model.programs.BotProgram;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class BotConsoleFancyFrame extends ConsoleFancyFrame {
    private final BotConsole console;
    private String selected;

    public BotConsoleFancyFrame(Player performingClient, BotConsole botConsole, GameData gameData) {
        super(performingClient.getFancyFrame(), botConsole, gameData, "black", "#27ff00");
        this.console = botConsole;


        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();

        content.append("<br>");
        content.append("Available Bot Programs:<br/>");
        for (BotProgram bp : console.getPrograms(gameData, player)) {
            content.append("# " + bp.getName() + "<br/>");
        }

        ScanBrainAction sba = new ScanBrainAction(console);
        if (sba.getOptions(gameData, player).numberOfSuboptions() > 0) {
            content.append("<br/>Brain Receptacle:<br/>");
            for (ActionOption opt : sba.getOptions(gameData, player).getSuboptions()) {
                String fgColor = "#27ff00";
                String bgColor = "black";
                if (opt.getName().equals(selected)) {
                    fgColor = "black";
                    bgColor = "#27ff00";
                }
                content.append(HTMLText.makeFancyFrameLink("SCAN " + opt.getName(),
                        HTMLText.makeText(fgColor, bgColor, "[scan]")) +
                        " " + opt.getName() + "<br/>");
            }
        }


        setData(console.getPublicName(player), false,
                HTMLText.makeText("green", "black", "courier", 3, content.toString()));
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("SCAN")) {
            String rest = event.replace("SCAN ", "");
            List<String> args = new ArrayList<>();
            args.add(rest);
            selected = rest;
            ScanBrainAction sba = new ScanBrainAction(console);
            sba.setArguments(args, player);
            player.setNextAction(sba);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        }
    }
}
