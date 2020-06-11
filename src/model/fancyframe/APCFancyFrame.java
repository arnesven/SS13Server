package model.fancyframe;

import model.GameData;
import model.Player;
import model.items.general.GameItem;
import model.items.general.Multimeter;
import model.items.general.Tools;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.power.AreaPowerControl;
import sounds.Sound;
import util.HTMLText;

public class APCFancyFrame extends FancyFrame {
    private final AreaPowerControl apc;

    public APCFancyFrame(Player performingClient, GameData gameData, AreaPowerControl apc) {
        super(performingClient.getFancyFrame());
        this.apc = apc;

        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player performingClient) {
        StringBuilder content = new StringBuilder();
        if (apc.isOpen()) {
            if (GameItem.hasAnItemOfClass(performingClient, Tools.class)) {
                content.append(HTMLText.makeFancyFrameLink("OPENCLOSE", "[close]"));
            }
            content.append("<br/>");
            content.append(HTMLText.makeCentered(makeWireBox(gameData, performingClient)));
        } else {
            if (GameItem.hasAnItemOfClass(performingClient, Tools.class)) {
                content.append(HTMLText.makeFancyFrameLink("OPENCLOSE", "[open]"));
            }
            content.append("<br/>");
            content.append(HTMLText.makeCentered(makeTable(gameData, performingClient)));
        }

        setData(apc.getPublicName(performingClient), false, HTMLText.makeColoredBackground("White", content.toString()));
    }

    private String makeWireBox(GameData gameData, Player performingClient) {
        StringBuilder box = new StringBuilder();
        box.append("<table width=\"75%\" bgcolor=\"#494949\" style=\"border:1px solid black\">");
        int index = 0;
        for (ElectricalMachinery obj : apc.getConnectedElectricalObjects()) {
            box.append("<tr>");
            if (GameItem.hasAnItemOfClass(performingClient, Multimeter.class)) {
                box.append("<td>" + HTMLText.makeText("yellow", obj.isPowered()?"1":obj.getAPCWire().getStateAsChar()) + "</td>");
            }
            box.append("<td><center>" + obj.getAPCWire().drawYourselfInHTML(performingClient, true) + "</center></td>");
            if (GameItem.hasAnItemOfClass(performingClient, Tools.class)) {
                if (obj.getAPCWire().isCut()) {
                    box.append(HTMLText.makeFancyFrameLink("MEND " + index, HTMLText.makeText("yellow", "[mend]")));
                } else {
                    box.append(HTMLText.makeFancyFrameLink("CUT " + index, HTMLText.makeText("yellow", "[cut]")));
                }
                index++;
            }
            box.append("</tr>");
        }
        box.append("</table>");
        return box.toString();
    }

    private String makeTable(GameData gameData, Player performingClient) {
        StringBuilder table = new StringBuilder();
        table.append("<table width=\"75%\" bgcolor=\"Blue\" style=\"border: 1px solid black\">");

        for (ElectricalMachinery obj : apc.getConnectedElectricalObjects()) {
            table.append(rowForObj(obj));
        }

        table.append("</table>");
        return table.toString();
    }

    private String rowForObj(ElectricalMachinery obj) {
        return "<tr><td style=\"padding:0px\">" + courierWhite(obj.getName()) + "</td><td style=\"text-align:right;padding:0px\">" +
                courierWhite(String.format("%dW", (int)(obj.getPowerConsumption() * 1000000))) + "</td><td style=\"text-align:right;padding:0px\">" +
                courierWhite(obj.isPowered()?"OK":HTMLText.makeText("red", "NO")) + "</td></tr>";
    }

    private String courierWhite(String name) {
        return HTMLText.makeText("white", "courier", 3, name);
    }

    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        buildContent(gameData, player);
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("OPENCLOSE")) {
            apc.setOpen(!apc.isOpen());
            player.getSoundQueue().add(new Sound("screwdriver"));
            player.refreshClientData();
            if (apc.isOpen()) {
                gameData.getChat().serverInSay("You unscrewed the display to the APC. Wow! Lots of wires...", player);
            } else {
                gameData.getChat().serverInSay("You mounted the display to the APC", player);
            }
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        } else if (event.contains("CUT")) {
            apc.getConnectedElectricalObjects().get(Integer.parseInt(event.replace("CUT ", ""))).getAPCWire().cut(player, gameData);
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        } else if (event.contains("MEND")) {
            apc.getConnectedElectricalObjects().get(Integer.parseInt(event.replace("MEND ", ""))).getAPCWire().mend(player, gameData);
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        }
    }
}
