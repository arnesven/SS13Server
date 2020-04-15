package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.AirlockOverrideAction;
import model.actions.objectactions.ApproveVentStation;
import model.map.rooms.AirLockRoom;
import model.map.rooms.Room;
import model.objects.consoles.AirLockControl;
import model.objects.consoles.Console;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AirLockControlFancyFrame extends ConsoleFancyFrame {
    private final AirLockControl airLockControl;
    private final AirlockOverrideAction airlockAction;
    private String selected = "";
    private boolean showVenting = false;

    public AirLockControlFancyFrame(Player pl, Console console, GameData gameData, String bgColor, String fgColor) {
        super(pl.getFancyFrame(), console, gameData, bgColor, fgColor);
        this.airLockControl = (AirLockControl)console;
        airlockAction = new AirlockOverrideAction(gameData);
        concreteRebuild(gameData, pl);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (showVenting) {
            showTheVenting(gameData, player, content);
        } else {
            showTheAirlocks(gameData, player, content);
        }
        setData(airLockControl.getPublicName(player), false, content.toString());
    }

    private void showTheVenting(GameData gameData, Player player, StringBuilder content) {
        content.append("________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE LOCKS", "[airlocks]"));
        content.append(HTMLText.makeCentered("<br/><b>Station Venting:</b><br/>"));
        if (airLockControl.mayApproveVenting(player)) {
            content.append(HTMLText.makeCentered(HTMLText.makeText("red", "Yellow", "Arial", 4,
                    "ATTENTION!<br/>Do not approve venting unless personnel safety has been confirmed!<br/><br/>") +
                            "(Approval last only this round and actual venting is done at Life Support console.)<br/><br/>" +
            HTMLText.makeFancyFrameLink("APPROVE", HTMLText.makeText("black", "gray", "Courier", 4, "[APPROVE]"))));
        } else {
            content.append(HTMLText.makeCentered("<i>Permission Denied</i>"));
        }

    }

    private void showTheAirlocks(GameData gameData, Player player, StringBuilder content) {
        content.append("________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE VENTING", "[venting]"));
        content.append("<br/><br/><b>Airlocks:</b><br/>");
        int i = 0;
        for (Room al : airlockAction.getRooms()) {
            String command = "Depressurize";
            if (airlockAction.getPanels().get(i).getPressure()) {
                command = "Pressurize";
            }
            String selectedColor = "blue";
            String fgColor = "white";
            if (selected.equals(al.getName())) {
                selectedColor = "white";
                fgColor = "blue";
            }
            content.append(HTMLText.makeText(fgColor, selectedColor, "Courier", 4,
                    HTMLText.makeFancyFrameLink("AIR " + al.getName(), "[" + command + "]")) + " " + al.getName() + "<br/>");
            i++;
        }

    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("CHANGEPAGE VENTING")) {
            showVenting = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("CHANGEPAGE LOCKS")) {
            showVenting = false;
            concreteRebuild(gameData, player);
        } else if (event.contains("AIR")) {
            AirlockOverrideAction aoa = new AirlockOverrideAction(gameData);
            List<String> args = new ArrayList<>();
            args.add(event.replace("AIR ", ""));
            aoa.setActionTreeArguments(args, player);
            selected = args.get(0);
            player.setNextAction(aoa);
            concreteRebuild(gameData, player);
            player.refreshClientData();
        } else if (event.contains("APPROVE")) {
            ApproveVentStation avs = new ApproveVentStation(airLockControl);
            player.setNextAction(avs);
            concreteRebuild(gameData, player);
            player.refreshClientData();
        }
        player.refreshClientData();
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        selected = "";
        concreteRebuild(gameData, actor);
    }
}
