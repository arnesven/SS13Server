package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.ActionOption;
import model.actions.objectactions.BeamUpAction;
import model.actions.objectactions.ScanSpaceAction;
import model.actions.objectactions.TeleportToCoordinatesAction;
import model.objects.consoles.Console;
import model.objects.consoles.TeleportConsole;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeleportConsoleFancyFrame extends ConsoleFancyFrame {
    private final TeleportConsole console;
    private boolean clickedScan = false;
    private boolean showTele = false;
    private boolean showBeam = false;

    public TeleportConsoleFancyFrame(Player performingClient, Console console, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "black", "white");
        this.console = (TeleportConsole)console;
        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (showTele) {
            content.append("<br/><br/>" + HTMLText.makeCentered("Enter destination coordinates below (integers, format X-Y-Z), or \"back\" to go cancel." +
                    "<br/>WARNING: You will be telported to these coordinates!"));
        } else if (showBeam) {
            BeamUpAction bua = new BeamUpAction(console, gameData);
            content.append("<br/>Detected Lifeforms:<br/>");
            for (ActionOption opt : bua.getOptions(gameData, player).getSuboptions()) {
                for (ActionOption innerOpt : opt.getSuboptions()) {
                    content.append(opt.getName() + " " +
                            HTMLText.makeFancyFrameLink("BUA " + opt.getName() + " " +
                                    innerOpt.getName(), "[BEAM UP]") + "<br/>" + innerOpt.getName() + "<br/>");
                }
            }
        } else {
            content.append("_____" + HTMLText.makeFancyFrameLink("TELE", "[TELEPORT]") + "____" +
                    HTMLText.makeFancyFrameLink("BEAM", "[BEAM UP]") + "<br/>");

            Integer[] coords = console.getLocalCoordinates(gameData);
            content.append("<br/>Local Coordinates are: " + coords[0] + "-" + coords[1] + "-" + coords[2] + "<br/>");
            content.append("Surrounding Space:<br/>");
            for (String s : console.getScannedData()) {
                content.append(s + "<br/>");
            }
            content.append(HTMLText.makeCentered(HTMLText.makeGrayButton(clickedScan, "SCAN", "[SCAN SPACE]")));
        }


        setData(console.getPublicName(player), showTele,
                HTMLText.makeText("white", "black", "Courier", 4, content.toString()));
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("SCAN")) {
            ScanSpaceAction ssa = new ScanSpaceAction(console);
            player.setNextAction(ssa);
            clickedScan = true;
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        } else if (event.contains("TELE")) {
            this.showTele = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("BEAM")) {
            this.showBeam = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("BUA")) {
            List<String> args = new ArrayList<>();
            String[] data = event.replace("BUA ", "").split(" ");
            args.add(data[0]);
            args.add(data[1]);
            BeamUpAction bua = new BeamUpAction(console, gameData);
            bua.setActionTreeArguments(args, player);
            player.setNextAction(bua);
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
        }
        player.refreshClientData();
    }

    @Override
    protected void consoleHandleInput(GameData gameData, Player player, String data) {
        if (showTele) {
            Logger.log("In show tele, got data: " + data);
            showTele = false;
            concreteRebuild(gameData, player);
            player.refreshClientData();
            Scanner scan = new Scanner(data);
            scan.useDelimiter("\\-");
            if (!scan.hasNextInt()) {
                return;
            }
            int x = scan.nextInt();
            if (!scan.hasNextInt()) {
                return;
            }
            int y = scan.nextInt();
            if (!scan.hasNextInt()) {
                return;
            }
            int z = scan.nextInt();
            Logger.log("Got coordinates " + x + " " + y + " " + z);
            if (0 <= x && x <= 299 && 0 <= y && y <= 299 && 0 <= z && z <= 299) {
                TeleportToCoordinatesAction ttca = new TeleportToCoordinatesAction();
                List<String> args = new ArrayList<>();
                args.add("(" + x + "-" + y + "-" + z + ")");
                ttca.setActionTreeArguments(args, player);
                player.setNextAction(ttca);
                readyThePlayer(gameData, player);
            }
        }
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        clickedScan = false;
        showTele = false;
        showBeam = false;
    }
}
