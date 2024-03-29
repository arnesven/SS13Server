package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.CallEscapeShuttleAction;
import model.actions.objectactions.MiningShuttleAction;
import model.actions.objectactions.MoveShuttleAction;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.EscapeShuttle;
import model.map.rooms.MiningShuttle;
import model.map.rooms.ShuttleRoom;
import model.objects.consoles.MiningShuttleControl;
import model.objects.consoles.ShuttleControlConsole;
import util.HTMLText;
import util.MyStrings;

import java.util.ArrayList;
import java.util.List;

public class ShuttleControlFancyFrame extends ConsoleFancyFrame {
    private final ShuttleControlConsole console;
    private final boolean hasAdvanced;
    private boolean showingAdvanced;
    private DockingPoint preferredDockingPoint;
    private String playerPrefers = null;
    private boolean hasCalled;

    public ShuttleControlFancyFrame(ShuttleControlConsole console, GameData gameData, Player performingClient, boolean hasAdvanced) {
        super(performingClient.getFancyFrame(), console, gameData, "#ca9f21", "black");
        this.console = console;
        this.hasAdvanced = hasAdvanced;
        showingAdvanced = false;
        hasCalled = false;

        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (showingAdvanced) {
            content.append("_________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE", "[back]"));
            showAdvancedView(gameData, player, content);
        } else {
            if (CallEscapeShuttleAction.canCallEscapeShuttle(player)) {
                content.append("____________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE", "[advanced]"));
            }
            showMiningShuttleView(gameData, player, content);
        }
        setData(console.getPublicName(player), false, content.toString());
    }


    private void showAdvancedView(GameData gameData, Player player, StringBuilder content) {
        content.append("<br/>");
        content.append(HTMLText.makeCentered("<b>Escape Shuttle</b>"));
        content.append(HTMLText.makeCentered(HTMLText.makeText("black", "serif", 2, "<i>Disclaimer: Do not call the escape shuttle unless absolutely necessary! " +
                "Nanotrasen will persecute abusers!</i>")));

        EscapeShuttle shuttle = null;
        try {
            shuttle = (EscapeShuttle) gameData.getMap().getRoom("Escape Shuttle");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        List<DockingPoint> otherDockingPoints = new ArrayList<>();
        preferredDockingPoint = findPreferredSS13DockingPoint(gameData, otherDockingPoints, shuttle, "Personnel");
        String destination;
        if (preferredDockingPoint == null) {
            destination = HTMLText.makeText("Yellow", "No available docking points at SS13!");
        } else {
            destination = "SS13 (" + preferredDockingPoint.getName() + ")";
        }
        content.append("<br/>");
        content.append("<b>Destination:</b> " + destination);
        if (preferredDockingPoint != null) {
            if (hasCalled) {
                content.append(HTMLText.makeCentered(HTMLText.makeText("yellow", "black", "[call]")));
            } else {
                content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("CALL " + preferredDockingPoint.getName(), "[call]")));
            }
        }
        content.append("<br/>");
        if (!hasCalled) {
            makeOtherDockingPointsTable(otherDockingPoints, content);
        }
    }

    private void showMiningShuttleView(GameData gameData, Player player, StringBuilder content) {
        ShuttleRoom shuttle = null;
        String docked = "Unknown location!";
        String destination = "Unknown location!";
        try {
            shuttle = (ShuttleRoom) gameData.getMap().getRoom(console.getShuttleName());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        try {
            List<DockingPoint> otherDockingPoints = new ArrayList<>();
            if (gameData.getMap().getLevelForRoom(shuttle).getName().equals(console.getLevelB())) {
                docked = MyStrings.capitalize(console.getLevelB()) + " (" + shuttle.getDockingPointRoom().getName() + ")";
                preferredDockingPoint = findPreferredSS13DockingPoint(gameData, otherDockingPoints, shuttle, "Cargo");
                if (preferredDockingPoint == null) {
                    destination = HTMLText.makeText("Yellow", "No available docking points at " + console.getLevelA() + "!");
                } else {
                    destination = console.getLevelA() + " (" + preferredDockingPoint.getName() + ")";
                }
            } else if (gameData.getMap().getLevelForRoom(shuttle).getName().equals(console.getLevelA())) {
                docked = MyStrings.capitalize(console.getLevelA()) + " (docked at " + shuttle.getDockingPointRoom().getName() + ")";
                preferredDockingPoint = findPreferredAsteroidDockingPoint(gameData, otherDockingPoints, shuttle);
                if (preferredDockingPoint == null) {
                    destination = HTMLText.makeText("Yellow", "No available docking points at " + console.getLevelB() + "!");
                } else {
                    destination = console.getLevelB() + " (" + preferredDockingPoint.getName() + ")";
                }
            }

            content.append("<br/>" +
                    "<b>Shuttle currently at: </b>" +
                    HTMLText.makeCentered(docked));

            if (preferredDockingPoint == null) {
                content.append(HTMLText.makeCentered(destination));
            } else {
                content.append("<b>Next stop: </b>" +
                        HTMLText.makeCentered(destination) +
                        HTMLText.makeCentered(HTMLText.makeFancyFrameLink("MOVE " + preferredDockingPoint.getName(),
                                "[Move " + console.getShuttleName() + "]")));
            }

            makeOtherDockingPointsTable(otherDockingPoints, content);

        } catch (NoSuchThingException nste) {
            content.append(HTMLText.makeCentered("ERROR! Could not find asteroid field!"));
        }
    }

    private void makeOtherDockingPointsTable(List<DockingPoint> otherDockingPoints, StringBuilder content) {
        content.append("<br/>Other available docking points:<br/>");
        for (DockingPoint dp : otherDockingPoints) {
            content.append(HTMLText.makeFancyFrameLink("PREF " + dp.getName(),
                    dp.getName() + " (" + dp.getDescription() + ")<br/>"));
        }
    }

    private DockingPoint findPreferredAsteroidDockingPoint(GameData gameData, List<DockingPoint> otherDockingPoints, ShuttleRoom shuttle) {
        DockingPoint pref = null;
        for (DockingPoint dp : gameData.getMap().getLevel(console.getLevelB()).getDockingPoints()) {
            if (dp.getName().equals(playerPrefers)) {
                pref = dp;
            } else if (dp.isVacant() && shuttle.canDockAt(gameData, dp)) {
                otherDockingPoints.add(dp);
            }
        }
        if (pref == null) {
            if (otherDockingPoints.size() != 0) {
                pref = otherDockingPoints.remove(0);
            }
        }
        return pref;
    }

    private DockingPoint findPreferredSS13DockingPoint(GameData gameData, List<DockingPoint> otherDockingPoints,
                                                       ShuttleRoom shuttleRoom, String preferredName) {
        DockingPoint pref = null;
        for (DockingPoint dp : gameData.getMap().getLevel(console.getLevelA()).getDockingPoints()) {
            if (dp.getName().equals(playerPrefers)) {
                pref = dp;
            } else {
                if (pref == null && dp.getDescription().equals(preferredName) && dp.isVacant() && shuttleRoom.canDockAt(gameData, dp)) {
                    pref = dp;
                } else if (shuttleRoom.canDockAt(gameData, dp)) {
                    otherDockingPoints.add(dp);
                }
            }
        }

        if (pref == null) {
            if (otherDockingPoints.size() != 0) {
                pref = otherDockingPoints.remove(0);
            }
        }
        return pref;
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        buildContent(gameData, player);
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("MOVE")) {
            MoveShuttleAction msa = console.getCorrespondingAction(gameData, player);
            List<String> args = new ArrayList<>();
            args.add(event.replace("MOVE ", ""));
            msa.setActionTreeArguments(args, player);
            player.setNextAction(msa);
            readyThePlayer(gameData, player);
        } else if (event.contains("PREF")) {
            this.playerPrefers = event.replace("PREF ", "");
            buildContent(gameData, player);
        } else if (event.contains("CHANGEPAGE")) {
            playerPrefers = null;
            showingAdvanced = !showingAdvanced;
            buildContent(gameData, player);
        } else if (event.contains("CALL")) {
            CallEscapeShuttleAction ceasa = new CallEscapeShuttleAction(gameData, (MiningShuttleControl) console);
            List<String> args = new ArrayList<>();
            args.add(event.replace("CALL ", ""));
            ceasa.setActionTreeArguments(args, player);
            player.setNextAction(ceasa);
            readyThePlayer(gameData, player);
            hasCalled = true;
            buildContent(gameData, player);
        }
    }


    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        super.doAtEndOfTurn(gameData, actor);
    }
}
