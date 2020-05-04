package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.AirlockOverrideAction;
import model.actions.objectactions.ApproveVentStation;
import model.map.rooms.Room;
import model.objects.consoles.AirLockConsole;
import model.objects.consoles.Console;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class AirLockControlFancyFrame extends ConsoleFancyFrame {
    private final AirLockConsole airLockControl;
    private final AirlockOverrideAction airlockAction;
    private String selected = "";
    private boolean showVenting = false;
    private boolean approved;

    public AirLockControlFancyFrame(Player pl, Console console, GameData gameData, String bgColor, String fgColor) {
        super(pl.getFancyFrame(), console, gameData, bgColor, fgColor);
        this.airLockControl = (AirLockConsole)console;
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
            String buttonColor = "gray";
            if (approved) {
                buttonColor = "white";
            }
            content.append(HTMLText.makeCentered(HTMLText.makeText("red", "Yellow", "Arial", 4,
                    "ATTENTION!<br/>Do not approve venting unless personnel safety has been confirmed!<br/><br/>") +
                            "(Approval last only this round and actual venting is done at Life Support console.)<br/><br/>" +
            HTMLText.makeFancyFrameLink("APPROVE", HTMLText.makeText("black", buttonColor, "Courier", 4, "[APPROVE]"))));
        } else {
            content.append(HTMLText.makeCentered("<i>Permission Denied</i>"));
        }

    }

    private void showTheAirlocks(GameData gameData, Player player, StringBuilder content) {
        content.append("________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE VENTING", "[venting]"));
        content.append("<br/><br/><b>Airlocks:</b><br/>");
        int i = 0;
        for (Room al : airlockAction.getKnownAirlocks()) {
            String command = "Depressurize";
            if (!airlockAction.getKnownAirlocks().get(i).hasPressure()) {
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
            readyThePlayer(gameData, player);
        } else if (event.contains("APPROVE")) {
            ApproveVentStation avs = new ApproveVentStation(airLockControl);
            approved = true;
            player.setNextAction(avs);
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
        }
        player.refreshClientData();
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        selected = "";
        approved = false;
      //  concreteRebuild(gameData, actor);
    }
}
