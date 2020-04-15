package model.fancyframe;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.VendingMachineAction;
import model.items.general.GameItem;
import model.objects.general.VendingMachine;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachineFancyFrame extends FancyFrame {
    private final VendingMachine vending;
    private int selectedIndex = -1;

    public VendingMachineFancyFrame(Player performingClient, VendingMachine vending) {
        super(performingClient.getFancyFrame());
        this.vending = vending;
        buildContent(performingClient, vending);
    }

    protected void buildContent(Player performingClient, VendingMachine vending) {
        StringBuilder content = new StringBuilder();

        content.append("<center>" + HTMLText.makeText("Yellow", "<b>Bell's Quality Stuff</b><br/><i>Great Prices!</i>") + "<br/><br/><table bgcolor=\"#000000\"><tr>");
        int col = 0;
        List<GameItem> rowList = new ArrayList<>();
        for (GameItem it : vending.getItems()) {
            String selectionColor = "#222222";
            if (selectedIndex == col) {
                selectionColor = "#444444";
            }

            content.append("<td bgcolor=\"" + selectionColor + "\">" + HTMLText.makeFancyFrameLink("BUY " + col ,
                    HTMLText.makeImage(getLookForItem(col, performingClient))) + "</td>");
            rowList.add(it);
            col++;
            if (col % 3 == 0) {
                content.append("</tr><tr>");
                for (GameItem it2: rowList) {
                    content.append("<td>" + HTMLText.makeText("Yellow", "$$ " + it2.getCost()) + "</td>");
                }
                content.append("</tr><tr>");
                rowList.clear();
            }
        }
        if (col % 3 != 0) {
            content.append("</tr><tr>");
            for (GameItem it2: rowList) {
                content.append("<td>" + HTMLText.makeText("Yellow", "$$ " + it2.getCost()) + "</td>");
            }
            content.append("</tr><tr>");
            rowList.clear();
        }
        content.append("</tr></table></center>");

        setData(vending.getPublicName(performingClient), false, HTMLText.makeColoredBackground("#a7281e", content.toString()));
    }

    protected Sprite getLookForItem(int col, Player p) {
        return vending.getItems().get(col).getSprite(p);
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("BUY")) {
            Logger.log(player.getName() + " is getting snacks!");
            selectedIndex = new Scanner(event.replace("BUY ", "")).nextInt();
            List<String> args = new ArrayList<>();
            GameItem selectedItem = vending.getItems().get(selectedIndex);
            args.add(selectedItem.getBaseName() + " ($$ " + selectedItem.getCost() + ")");
            VendingMachineAction vma = new VendingMachineAction(vending);
            vma.setArguments(args, player);
            player.setNextAction(vma);
            readyThePlayer(gameData, player);
            buildContent(player, vending);
            player.refreshClientData();
        }
    }
}
