package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.OrderShipmentAction;
import model.objects.consoles.Console;
import model.objects.consoles.RequisitionsConsole;
import model.objects.shipments.Shipment;
import util.HTMLText;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequisitionsFancyFrame extends ConsoleFancyFrame {
    private final RequisitionsConsole reqConsole;
    private int selectedIndex = -1;
    private boolean onHistoryPage = false;

    public RequisitionsFancyFrame(Player player, Console console, GameData gameData) {
        super(player.getFancyFrame(), console, gameData, "#00198a", "#a3f4ff");
        this.reqConsole = (RequisitionsConsole)console;
        concreteRebuild(gameData, player);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (onHistoryPage) {
            content.append("________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE BACK", "[back]"));
            content.append("<br/><b>Requisitions History:</b><br/>");
            for (Pair<Actor, Shipment> p : reqConsole.getHistory()) {
                content.append(p.first.getBaseName() + " ordered " + p.second.getName() + "<br/>");
            }

        } else {
            content.append("_______________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE HISTORY", "[history]"));
            content.append("<br><b>Station Funds:</b> $$ " + reqConsole.getMoney() + "<br/>");
            int i = 0;
            for (Shipment s : reqConsole.getShipments()) {
                String buttonText = "[buy]";
                if (this.selectedIndex == i) {
                    buttonText = "<b>[buy]</b>";
                }

                content.append(HTMLText.makeImage(s.getSprite()) + " " +
                        HTMLText.makeFancyFrameLink("BUY " + (i++), buttonText) + " " +
                        s.getName() + " $$ " + s.getCost() + "<br/>");
            }
        }
        setData(reqConsole.getPublicName(player), false, content.toString());
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("BUY")) {
            String rest = event.replace("BUY ", "");
            selectedIndex = new Scanner(rest).nextInt();
            OrderShipmentAction osa = new OrderShipmentAction(reqConsole);
            List<String> args = new ArrayList<>();
            args.add(reqConsole.getShipments().get(selectedIndex).getName());
            osa.setActionTreeArguments(args, player);
            player.setNextAction(osa);
            player.refreshClientData();
            readyThePlayer(gameData, player);
            rebuildInterface(gameData, player);
        } else if (event.contains("CHANGEPAGE HISTORY")) {
            this.onHistoryPage = true;
            rebuildInterface(gameData, player);
        } else if (event.contains("CHANGEPAGE BACK")) {
            this.onHistoryPage = false;
            rebuildInterface(gameData, player);
        }
    }
}
