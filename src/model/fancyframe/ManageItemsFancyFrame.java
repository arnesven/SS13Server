package model.fancyframe;

import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DropAction;
import model.actions.general.MultiAction;
import model.actions.general.PickUpAction;
import model.actions.objectactions.RecycleAction;
import model.actions.objectactions.RetrieveAction;
import model.actions.objectactions.StoreItemAction;
import model.items.general.GameItem;
import model.objects.decorations.TrashBag;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;
import model.objects.recycling.TrashBin;
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
    private Set<GameItem> recyclings;
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
        recyclings = new HashSet<>();
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

        makeFirstTable(content, gameData, performingClient, isRecyclingApplicable(firstItemHolder, performingClient));
        makeSecondTable(content, gameData, performingClient, isRecyclingApplicable(secondItemHolder, performingClient));

        if (!playerConfirmed) {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("CONFIRM", HTMLText.makeText("yellow", "serif", 5 , "| DONE |"))));
        } else {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("CONFIRM", HTMLText.makeText("black", "yellow", "serif",5, "DONE"))));
        }

        setData("Manage Items", false, HTMLText.makeColoredBackground("black",
                HTMLText.makeText("Yellow", content.toString())));
    }

    private boolean isRecyclingApplicable(ItemHolder firstItemHolder, Player performingClient) {
        return firstItemHolder == performingClient && TrashBin.hasTrashCan(performingClient.getPosition());
    }


    private void makeFirstTable(StringBuilder content, GameData gameData, Player performingClient, boolean withRecycle) {
        content.append(HTMLText.makeBox("yellow", "gray", HTMLText.makeCentered(HTMLText.makeText("yellow", "<b>" + firstTitle + "</b>"))));
        if (firstItemHolder.getItems().isEmpty()) {
            content.append(HTMLText.makeCentered(HTMLText.makeText("Yellow", "<i>No Items!</i>")));
            return;
        }
        content.append("<table color=\"yellow\" width=\"100%\">");
        if (maxAllowedPuts == Integer.MAX_VALUE) {
            content.append("<tr><td></td><td></td><td></td><td>" +
                    HTMLText.makeFancyFrameLink("CHECKALL FIRST",
                            HTMLText.makeText("yellow" , "[all]")) + "</td></tr>");
        }
        for (GameItem gi : firstItemHolder.getItems()) {
            content.append("<tr>");
            content.append("<td>" + HTMLText.makeImage(gi.getSprite(performingClient)) + "</td>");
            content.append("<td>" + firstNameGetter.getName(gi, performingClient) + "</td>");
            content.append("<td>" + String.format("%1.1f kg", gi.getWeight()) + "</td>");
            if (!recyclings.contains(gi)) {
                if (puttings.size() < maxAllowedPuts || puttings.contains(gi)) {
                    content.append("<td>" + makeCheckBox("DROP", "KEEP",
                            puttings.contains(gi), performingClient, gi, playerConfirmed) + "</td>");
                }
            }
            content.append(makeRecycleButton(gi, withRecycle, performingClient));
            content.append("</tr>");
        }
        content.append("</table>");
    }


    private void makeSecondTable(StringBuilder content, GameData gameData, Player performingClient, boolean withRecycle) {
        content.append(HTMLText.makeBox("yellow", "gray",
                HTMLText.makeCentered(HTMLText.makeText("yellow", "<b>" + secondTitle + "</b>"))));
        if (secondItemHolder.getItems().isEmpty()) {
            content.append(HTMLText.makeCentered(HTMLText.makeText("Yellow", "<i>No Items!</i>")));
            return;
        }
        content.append("<table color=\"yellow\" width=\"100%\">");
        if (maxAllowedGets == Integer.MAX_VALUE) {
            content.append("<tr><td></td><td></td><td></td><td>" +
                    HTMLText.makeFancyFrameLink("CHECKALL SECOND",
                            HTMLText.makeText("yellow" , "[all]")) + "</td></tr>");
        }
        for (GameItem gi : secondItemHolder.getItems()) {
            content.append("<tr>");
            content.append("<td>" + HTMLText.makeImage(gi.getSprite(performingClient)) + "</td>");
            content.append("<td>" + secondNameGetter.getName(gi, performingClient) + "</td>");
            content.append("<td>" + String.format("%1.1f kg", gi.getWeight()) + "</td>");
            if (!recyclings.contains(gi)) {
                if (gettings.size() < maxAllowedGets || gettings.contains(gi)) {
                    content.append("<td>" + makeCheckBox("PICKUP", "DONTPICKUP",
                            gettings.contains(gi), performingClient, gi, playerConfirmed) + "</td>");
                }
            }
            content.append(makeRecycleButton(gi, withRecycle, performingClient));
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
                gi.getUniqueName(performingClient), HTMLText.makeText("yellow", "arial", 6,symbol));
    }

    private String makeRecycleButton(GameItem gi, boolean withRecycle, Player player) {
        if (withRecycle) {
            String rec = "♻";
            if (recyclings.contains(gi)) {
                return "<td>" + HTMLText.makeFancyFrameLink("DONTRECYCLE " + gi.getUniqueName(player),
                        HTMLText.makeText("black", "green", "serif", 6, rec)) + "</td>";
            } else {
                return "<td>" + HTMLText.makeFancyFrameLink("RECYCLE " + gi.getUniqueName(player),
                        HTMLText.makeText("green", "serif", 6, rec)) + "</td>";
            }
        }
        return "";
    }



    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("DROP")) {
            for (GameItem gi : firstItemHolder.getItems()) {
                if (gi.getUniqueName(player).equals(event.replace("DROP ", ""))) {
                    puttings.add(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("KEEP")) {
            for (GameItem gi : puttings) {
                if (gi.getUniqueName(player).equals(event.replace("KEEP ", ""))) {
                    puttings.remove(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("CHECKALL")) {
            String rest = event.replace("CHECKALL ", "");
            if (rest.equals("FIRST")) {
                puttings.addAll(firstItemHolder.getItems());
            } else {
                gettings.addAll(secondItemHolder.getItems());
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("DONTPICKUP")) {
            Logger.log("Got dontpickup event");
            for (GameItem gi : gettings) {
                Logger.log("Checking " + gi.getUniqueName(player));
                if (gi.getUniqueName(player).equals(event.replace("DONTPICKUP ", ""))) {
                    gettings.remove(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("PICKUP")) {
            for (GameItem gi : secondItemHolder.getItems()) {
                if (gi.getUniqueName(player).equals(event.replace("PICKUP ", ""))) {
                    gettings.add(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("DONTRECYCLE")) {
            for (GameItem gi : recyclings) {
                if (gi.getUniqueName(player).equals(event.replace("DONTRECYCLE ", ""))) {
                    recyclings.remove(gi);
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("RECYCLE")) {
            Set<GameItem> all = new HashSet<>();
            all.addAll(firstItemHolder.getItems());
            all.addAll(secondItemHolder.getItems());
            for (GameItem gi : all) {
                if (gi.getUniqueName(player).equals(event.replace("RECYCLE ", ""))) {
                    recyclings.add(gi);
                    if (gettings.contains(gi)) {
                        gettings.remove(gi);
                    }
                    if (puttings.contains(gi)) {
                        puttings.remove(gi);
                    }
                    break;
                }
            }
            rebuildInterface(gameData, player);
        } else if (event.contains("CONFIRM")) {
            playerConfirmed = !playerConfirmed;
            MultiAction multiAction = getFinalAction(gameData, player, puttings, gettings);
            for (GameItem gi : recyclings) {
                multiAction.addAction(makeRecycleAction(gi, player));
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

    protected Action makeDropAction(GameItem it, Player player) {
        DropAction da = new DropAction(player);
        List<String> args = new ArrayList<>();
        args.add(it.getFullName(player));
        da.setActionTreeArguments(args, player);
        return da;
    }

    private Action makeRecycleAction(GameItem gi, Player player) {
        TrashBin bin = null;
        for (GameObject obj : player.getPosition().getObjects()) {
            if (obj instanceof TrashBin) {
                bin = (TrashBin) obj;
            }
        }
        RecycleAction ra = new RecycleAction(bin);
        List<String> args = new ArrayList<>();
        args.add(gi.getFullName(player));
        ra.setActionTreeArguments(args, player);
        return ra;
    }


    protected Action makePickUpAction(GameItem it, Player player) {
        PickUpAction pua = new PickUpAction(player);
        List<String> args = new ArrayList<>();
        args.add(it.getPublicName(player));
        pua.setActionTreeArguments(args, player);
        return pua;
    }

    protected Action makeStoreAction(GameItem gi, GameData gameData, Player player, ItemHolder container) {
        StoreItemAction sia = new StoreItemAction(container, player);
        List<String> args = new ArrayList<>();
        args.add(gi.getFullName(player));
        sia.setActionTreeArguments(args, player);
        return sia;
    }

    protected Action makeRetrieveAction(GameItem gi, ContainerObject container, Player player) {
        RetrieveAction ra = new RetrieveAction(container, player);
        List<String> args = new ArrayList<>();
        args.add(gi.getPublicName(player));
        ra.setActionTreeArguments(args, player);
        return ra;

    }

    protected interface NameGetter extends Serializable {
        String getName(GameItem gi, Player pl);
    }
}
