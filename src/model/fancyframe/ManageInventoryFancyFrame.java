package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.DropAction;
import model.actions.general.MultiAction;
import model.actions.general.PickUpAction;
import model.items.general.GameItem;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManageInventoryFancyFrame extends FancyFrame {
    private final boolean pickupFirst;
    private Set<GameItem> dropping;
    private Set<GameItem> pickupping;
    private boolean playerConfirmed = false;
    private int MAX_ALLOWED_PICKUPS = 1;

    public ManageInventoryFancyFrame(Player performingClient, GameData gameData, boolean pickupFirst) {
        super(performingClient.getFancyFrame());
        dropping = new HashSet<>();
        pickupping = new HashSet<>();
        this.pickupFirst = pickupFirst;

        setSize(400, 450);
        buildInterface(gameData, performingClient);
    }

    private void buildInterface(GameData gameData, Player performingClient) {
        StringBuilder content = new StringBuilder();

        if (pickupFirst) {
            makeRoomItemsTable(content, gameData, performingClient);
            makeInventoryTable(content, gameData, performingClient);
        } else {
            makeInventoryTable(content, gameData, performingClient);
            makeRoomItemsTable(content, gameData, performingClient);
        }

        if (!playerConfirmed) {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("CONFIRM", HTMLText.makeText("yellow", "serif", 5 , "| DONE |"))));
        } else {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("CONFIRM", HTMLText.makeText("black", "yellow", "serif",5, "DONE"))));
        }

        setData("Manage Inventory - Drop to floor", false, HTMLText.makeColoredBackground("black",
                HTMLText.makeText("Yellow", content.toString())));
    }


    private void makeInventoryTable(StringBuilder content, GameData gameData, Player performingClient) {
        content.append(HTMLText.makeBox("yellow", "gray", HTMLText.makeCentered(HTMLText.makeText("yellow", "<b>Inventory - Dropping</b>"))));
        content.append("<table color=\"yellow\" width=\"100%\">");
        for (GameItem gi : performingClient.getItems()) {
            content.append("<tr>");
            content.append("<td>" + HTMLText.makeImage(gi.getSprite(performingClient)) + "</td>");
            content.append("<td>" + gi.getFullName(performingClient) + "</td>");
            content.append("<td>" + String.format("%1.1f kg", gi.getWeight()) + "</td>");
            content.append("<td>" + makeCheckBox("DROP", "KEEP", dropping.contains(gi), performingClient, gi, playerConfirmed) +"</td>");
            content.append("</tr>");
        }
        content.append("</table>");
    }

    private String makeCheckBox(String commandFalse, String commandTrue, boolean predicate,
                                Player performingClient, GameItem gi, boolean disable) {

        String command = commandFalse;
        String symbol = "☐";
        if (predicate) {
            command = commandTrue;
            symbol = "☑";
        }
        if (disable) {
            return HTMLText.makeText("gray", "arial", 6, symbol);
        }
        return HTMLText.makeFancyFrameLink(command + " " +
                gi.getFullName(performingClient), HTMLText.makeText("yellow", "arial", 6,symbol));
    }

    private void makeRoomItemsTable(StringBuilder content, GameData gameData, Player performingClient) {
        content.append(HTMLText.makeBox("yellow", "gray", HTMLText.makeCentered(HTMLText.makeText("yellow", "<b>Room Items - Pick Up (max 1)</b>"))));
        content.append("<table color=\"yellow\" width=\"100%\">");
        for (GameItem gi : performingClient.getPosition().getItems()) {
            content.append("<tr>");
            content.append("<td>" + HTMLText.makeImage(gi.getSprite(performingClient)) + "</td>");
            content.append("<td>" + gi.getPublicName(performingClient) + "</td>");
            content.append("<td>" + String.format("%1.1f kg", gi.getWeight()) + "</td>");
            if (pickupping.size() < MAX_ALLOWED_PICKUPS || pickupping.contains(gi)) {
                content.append("<td>" + makeCheckBox("PICKUP", "DONTPICKUP", pickupping.contains(gi), performingClient, gi, playerConfirmed) + "</td>");
            }
            content.append("</tr>");
        }
        content.append("</table>");
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("DROP")) {
            for (GameItem gi : player.getItems()) {
                if (gi.getFullName(player).equals(event.replace("DROP ", ""))) {
                    dropping.add(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("KEEP")) {
            for (GameItem gi : dropping) {
                if (gi.getFullName(player).equals(event.replace("KEEP ", ""))) {
                    dropping.remove(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("DONTPICKUP")) {
            Logger.log("Got dontpickup event");
            for (GameItem gi : pickupping) {
                Logger.log("Checking " + gi.getFullName(player));
                if (gi.getFullName(player).equals(event.replace("DONTPICKUP ", ""))) {
                    pickupping.remove(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("PICKUP")) {
            for (GameItem gi : player.getPosition().getItems()) {
                if (gi.getFullName(player).equals(event.replace("PICKUP ", ""))) {
                    pickupping.add(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("CONFIRM")) {
            playerConfirmed = !playerConfirmed;
            MultiAction multiAction = new MultiAction("Manage Inventory");
            for (GameItem it : dropping) {
                DropAction da = new DropAction(player);
                List<String> args = new ArrayList<>();
                args.add(it.getFullName(player));
                da.setActionTreeArguments(args, player);
                multiAction.addAction(da);
            }

            for (GameItem it : pickupping) {
                PickUpAction pua = new PickUpAction(player);
                List<String> args = new ArrayList<>();
                args.add(it.getPublicName(player));
                pua.setActionTreeArguments(args, player);
                multiAction.addAction(pua);
            }
            super.dismissAtEndOfTurn(gameData, player);
            player.setNextAction(multiAction);
            readyThePlayer(gameData, player);
            rebuildInterface(gameData, player);
        }
    }

    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        buildInterface(gameData, player);
    }

}
