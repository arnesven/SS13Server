package model.fancyframe;

import model.GameData;
import model.Player;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.power.AreaPowerControl;
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
        content.append("<br/>");

        content.append(HTMLText.makeCentered(makeTable(gameData, performingClient)));

        setData(apc.getPublicName(performingClient), false, HTMLText.makeColoredBackground("White", content.toString()));
    }

    private String makeTable(GameData gameData, Player performingClient) {
        StringBuilder table = new StringBuilder();
        table.append("<table width=\"75%\" bgcolor=\"Blue\" style=\"border: 1px solid black\">");
        for (GameObject obj : apc.getRoom1().getObjects()) {
            if (obj instanceof ElectricalMachinery) {
                table.append(rowForObj((ElectricalMachinery)obj));
            }
        }
        for (GameObject obj : apc.getRoom2().getObjects()) {
            if (obj instanceof ElectricalMachinery) {
                table.append(rowForObj((ElectricalMachinery)obj));
            }
        }

        table.append("</table>");
        return table.toString();
    }

    private String rowForObj(ElectricalMachinery obj) {
        return "<tr><td>" + courierWhite(obj.getName()) + "</td><td style=\"text-align:right\">" +
                courierWhite(String.format("%dW", (int)(obj.getPowerConsumption() * 1000000))) + "</td><td>" +
                courierWhite(obj.isPowered()?"OK":HTMLText.makeText("red", "NO")) + "</td></tr>";
    }

    private String courierWhite(String name) {
        return HTMLText.makeText("white", "courier", 3, name);
    }

    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        buildContent(gameData, player);
    }
}
