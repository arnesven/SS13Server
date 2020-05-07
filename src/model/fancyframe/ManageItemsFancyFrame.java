package model.fancyframe;

import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DropAction;
import model.actions.general.MultiAction;
import model.actions.general.PickUpAction;
import model.items.general.GameItem;
import util.HTMLText;
import util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ManageItemsFancyFrame extends FancyFrame {

    private final String firstTitle;
    private final String secondTitle;
    private final ItemHolder firstItemHolder;
    private final ItemHolder secondItemHolder;
    private final NameGetter firstNameGetter;
    private final NameGetter secondNameGetter;
    private Set<GameItem> puttings;
    private Set<GameItem> gettings;
    private boolean playerConfirmed = false;
    private final int maxAllowedGets;
    private final int maxAllowedPuts;

    public ManageItemsFancyFrame(Player performingClient, GameData gameData,
                                 String firstTitle, ItemHolder firstItemHolder, NameGetter firstNameGetter, int maxPuts,
                                 String secondTitle, ItemHolder secondItemHolder, NameGetter secondNameGetter, int maxGets,
                                 GameItem preselected) {
        super(performingClient.getFancyFrame());
        puttings = new HashSet<>();
        if (preselected != null) {
            puttings.add(preselected);
        }
        gettings = new HashSet<>();
        this.firstTitle = firstTitle;
        this.firstItemHolder = firstItemHolder;
        this.firstNameGetter = firstNameGetter;
        this.secondTitle = secondTitle;
        this.secondItemHolder = secondItemHolder;
        this.secondNameGetter = secondNameGetter;
        this.maxAllowedGets = maxGets;
        this.maxAllowedPuts = maxPuts;

        setSize(400, 450);
        buildInterface(gameData, performingClient);
    }


    protected abstract MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings);


    private void buildInterface(GameData gameData, Player performingClient) {
        StringBuilder content = new StringBuilder();

        makeFirstTable(content, gameData, performingClient);
        makeSecondTable(content, gameData, performingClient);

        if (!playerConfirmed) {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("CONFIRM", HTMLText.makeText("yellow", "serif", 5 , "| DONE |"))));
        } else {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("CONFIRM", HTMLText.makeText("black", "yellow", "serif",5, "DONE"))));
        }

        setData("Manage Items", false, HTMLText.makeColoredBackground("black",
                HTMLText.makeText("Yellow", content.toString())));
    }



    private void makeFirstTable(StringBuilder content, GameData gameData, Player performingClient) {
        content.append(HTMLText.makeBox("yellow", "gray", HTMLText.makeCentered(HTMLText.makeText("yellow", "<b>" + firstTitle + "</b>"))));
        content.append("<table color=\"yellow\" width=\"100%\">");
        if (firstItemHolder.getItems().isEmpty()) {
            content.append(HTMLText.makeCentered("<i>No Items!</i>"));
        }
        for (GameItem gi : firstItemHolder.getItems()) {
            content.append("<tr>");
            content.append("<td>" + HTMLText.makeImage(gi.getSprite(performingClient)) + "</td>");
            content.append("<td>" + firstNameGetter.getName(gi, performingClient) + "</td>");
            content.append("<td>" + String.format("%1.1f kg", gi.getWeight()) + "</td>");
            if (puttings.size() < maxAllowedPuts || puttings.contains(gi)) {
                content.append("<td>" + makeCheckBox("DROP", "KEEP",
                        puttings.contains(gi), performingClient, gi, playerConfirmed) + "</td>");
            }
            content.append("</tr>");
        }
        content.append("</table>");
    }


    private void makeSecondTable(StringBuilder content, GameData gameData, Player performingClient) {
        content.append(HTMLText.makeBox("yellow", "gray",
                HTMLText.makeCentered(HTMLText.makeText("yellow", "<b>" + secondTitle + "</b>"))));
        content.append("<table color=\"yellow\" width=\"100%\">");
        if (secondItemHolder.getItems().isEmpty()) {
            content.append(HTMLText.makeCentered("<i>No Items!</i>"));
        }
        for (GameItem gi : secondItemHolder.getItems()) {
            content.append("<tr>");
            content.append("<td>" + HTMLText.makeImage(gi.getSprite(performingClient)) + "</td>");
            content.append("<td>" + secondNameGetter.getName(gi, performingClient) + "</td>");
            content.append("<td>" + String.format("%1.1f kg", gi.getWeight()) + "</td>");
            if (gettings.size() < maxAllowedGets || gettings.contains(gi)) {
                content.append("<td>" + makeCheckBox("PICKUP", "DONTPICKUP",
                        gettings.contains(gi), performingClient, gi, playerConfirmed) + "</td>");
            }
            content.append("</tr>");
        }
        content.append("</table>");
    }



    protected String makeCheckBox(String commandFalse, String commandTrue, boolean predicate,
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



    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("DROP")) {
            for (GameItem gi : firstItemHolder.getItems()) {
                if (gi.getFullName(player).equals(event.replace("DROP ", ""))) {
                    puttings.add(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("KEEP")) {
            for (GameItem gi : puttings) {
                if (gi.getFullName(player).equals(event.replace("KEEP ", ""))) {
                    puttings.remove(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("DONTPICKUP")) {
            Logger.log("Got dontpickup event");
            for (GameItem gi : gettings) {
                Logger.log("Checking " + gi.getFullName(player));
                if (gi.getFullName(player).equals(event.replace("DONTPICKUP ", ""))) {
                    gettings.remove(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("PICKUP")) {
            for (GameItem gi : secondItemHolder.getItems()) {
                if (gi.getFullName(player).equals(event.replace("PICKUP ", ""))) {
                    gettings.add(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("CONFIRM")) {
            playerConfirmed = !playerConfirmed;
            MultiAction multiAction = getFinalAction(gameData, player, puttings, gettings);
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

    protected Action makeDropAction(GameItem it, Player player) {
        DropAction da = new DropAction(player);
        List<String> args = new ArrayList<>();
        args.add(it.getFullName(player));
        da.setActionTreeArguments(args, player);
        return da;
    }

    protected Action makePickUpAction(GameItem it, Player player) {
        PickUpAction pua = new PickUpAction(player);
        List<String> args = new ArrayList<>();
        args.add(it.getPublicName(player));
        pua.setActionTreeArguments(args, player);
        return pua;
    }

    protected interface NameGetter extends Serializable {
        String getName(GameItem gi, Player pl);
    }
}
