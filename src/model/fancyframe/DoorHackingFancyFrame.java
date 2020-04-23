package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.general.GameItem;
import model.items.general.GeigerMeter;
import model.items.general.Tools;
import model.map.doors.ElectricalDoor;
import model.map.doors.PowerCord;
import util.HTMLText;


public class DoorHackingFancyFrame extends FancyFrame {
    private final ElectricalDoor door;
    private boolean doorStateChanging = false;

    public DoorHackingFancyFrame(Player performingClient, ElectricalDoor door, GameData gameData) {
        super(performingClient.getFancyFrame());
        this.door = door;
        this.setHeight(300);
        this.setWidth(250);

        buildInterface(gameData, performingClient);
    }


    private void buildInterface(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();

        if (doorStateChanging) {
            content.append(HTMLText.makeCentered("<br/><br/><i>The door has been hacked and something is happening...</i>"));
        } else {
            content.append(HTMLText.makeCentered("Diode: " + door.getDiodeColor()));
            content.append("<table>");
            int index = 0;
            for (PowerCord pc : door.getDoorMechanism().getPowerCords()) {
                content.append("<tr>");
                content.append(getStateIfPlayerHasMultitool(player, pc));
                content.append("<td>" + pc.drawYourselfInHTML(player) + "</td>");
                content.append(addCutIfPlayerHasTools(player, index, pc));
                content.append(addPulseIfPlayerHasMultitool(player, index));
                content.append("</tr>");
                index++;
            }
            content.append("</table>");
        }

        setData("Door Mechanism " + door.getNumber(), false,
                HTMLText.makeColoredBackground("#dcdcdc", content.toString()));
    }

    private String addPulseIfPlayerHasMultitool(Player player, int index) {
        if (GameItem.hasAnItemOfClass(player, GeigerMeter.class)) {
            return "<td>" + HTMLText.makeFancyFrameLink("PULSE " + index, "[pulse]") + "</td>";
        }
        return "<td></td>";
    }

    private String addCutIfPlayerHasTools(Player player, int index, PowerCord pc) {
        if (GameItem.hasAnItemOfClass(player, Tools.class) && !pc.isCut()) {
            return "<td>" + HTMLText.makeFancyFrameLink("CUT " + index, "[cut]") + "</td>";
        }
        return "<td></td>";
    }

    private String getStateIfPlayerHasMultitool(Player player, PowerCord pc) {
        if (pc.isCut()) {
            return "<td>U</td>";
        } else if (GameItem.hasAnItemOfClass(player, GeigerMeter.class)) {
            return "<td>" + (pc.getState()==-1?"U":pc.getState()+"") + "</td>";
        } else  {
            return "<td>?</td>";
        }
    }


    @Override
    public void leaveFancyFrame(GameData gameData, Player pl) {
        super.leaveFancyFrame(gameData, pl);
        door.getDoorMechanism().setFancyFrameVacant();
    }

    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        buildInterface(gameData, player);
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        doorStateChanging = false;
        buildInterface(gameData, actor);
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("CUT")) {
            Action act = door.getDoorMechanism().cutCord(Integer.parseInt(event.replace("CUT ", "")), player, gameData);
            if (act != null) {
                player.setNextAction(act);
                this.doorStateChanging = true;
            }
            player.refreshClientData();
            buildInterface(gameData, player);
            readyThePlayer(gameData, player);
        }
    }
}
