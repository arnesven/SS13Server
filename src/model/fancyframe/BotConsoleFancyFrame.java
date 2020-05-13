package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.ActionOption;
import model.actions.objectactions.ScanBrainAction;
import model.map.rooms.StationRoom;
import model.npcs.NPC;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.BotConsole;
import model.programs.BotProgram;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class BotConsoleFancyFrame extends ConsoleFancyFrame {
    private final BotConsole console;
    private String selected;
    private boolean showingPrograms;

    public BotConsoleFancyFrame(Player performingClient, BotConsole botConsole, GameData gameData) {
        super(performingClient.getFancyFrame(), botConsole, gameData, "black", "#00ff2e");
        this.console = botConsole;
        showingPrograms = false;

        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();

        if (showingPrograms) {
            content.append("__________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE BOTS", "[bots]"));
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
        } else {
            content.append("________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE BOTS", "[programs]"));
            content.append("<br>");
            content.append("Connected Bots:<br/>");
            for (NPC npc : gameData.getNPCs()) {
                if (npc instanceof RobotNPC && npc.getPosition() instanceof StationRoom) {
                    content.append(npc.getInnermostCharacter().getBaseName() + "<br/>");
                }
            }

        }


        setData(console.getPublicName(player), false,
                HTMLText.makeText("#00ff2e", "black", "courier", 3, content.toString()));
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
        } else if (event.contains("CHANGEPAGE")) {
            showingPrograms = !showingPrograms;
            concreteRebuild(gameData, player);
        }
    }
}
