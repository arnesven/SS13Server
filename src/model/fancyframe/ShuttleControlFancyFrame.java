package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.MiningShuttleAction;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.MiningShuttle;
import model.objects.consoles.ShuttleControl;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class ShuttleControlFancyFrame extends ConsoleFancyFrame {
    private final ShuttleControl console;
    private DockingPoint preferredDockingPoint;
    private String playerPrefers = null;

    public ShuttleControlFancyFrame(ShuttleControl console, GameData gameData, Player performingClient) {
        super(performingClient.getFancyFrame(), console, gameData,"#ca9f21", "black");
        this.console = console;

        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player player) {
        MiningShuttle shuttle = null;
        String docked = "Unknown location!";
        String destination = "Unknown location!";
        try {
            shuttle = (MiningShuttle)gameData.getMap().getRoom("Mining Shuttle");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        try {
            List<DockingPoint> otherDockingPoints = new ArrayList<>();
            if (gameData.getMap().getLevelForRoom(shuttle).getName().equals("asteroid field")) {
                docked = "Asteroid Field (" + shuttle.getDockingPointRoom().getName() + ")";
                preferredDockingPoint = findPreferredSS13DockingPoint(gameData, otherDockingPoints, shuttle);
                if (preferredDockingPoint == null) {
                    destination = HTMLText.makeText("Yellow", "No available docking points at SS13!");
                } else {
                    destination = "SS13 (" + preferredDockingPoint.getName() + ")";
                }
            } else if (gameData.getMap().getLevelForRoom(shuttle).getName().equals(GameMap.STATION_LEVEL_NAME)) {
                docked = "SS13 (docked at " + shuttle.getDockingPointRoom().getName() + ")";
                preferredDockingPoint = findPreferredAsteroidDockingPoint(gameData, otherDockingPoints, shuttle);
                if (preferredDockingPoint == null) {
                    destination = HTMLText.makeText("Yellow", "No available docking points at Mining Station!");
                } else {
                    destination = "Asteroid Field (" + preferredDockingPoint.getName() + ")";
                }
            }

            StringBuilder content = new StringBuilder();
            content.append("<br/>" +
                            "<b>Shuttle currently at: </b>" +
                    HTMLText.makeCentered(docked));

            if (preferredDockingPoint == null) {
                content.append(HTMLText.makeCentered(destination));
            } else {
                content.append("<b>Next stop: </b>" +
                        HTMLText.makeCentered(destination) +
                        HTMLText.makeCentered(HTMLText.makeFancyFrameLink("MOVE " + preferredDockingPoint.getName(),
                                "[Move Mining Shuttle]")));
            }

            content.append("<br/>Other available docking points:<br/>");
            for (DockingPoint dp : otherDockingPoints) {
                content.append(HTMLText.makeFancyFrameLink("PREF " + dp.getName(),
                        dp.getName() + " (" + dp.getDescription() + ")<br/>"));
            }

            setData(console.getPublicName(player), false, content.toString());

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private DockingPoint findPreferredAsteroidDockingPoint(GameData gameData, List<DockingPoint> otherDockingPoints, MiningShuttle shuttle) {
        DockingPoint pref = null;
        for (DockingPoint dp : gameData.getMap().getLevel("asteroid field").getDockingPoints()) {
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

    private DockingPoint findPreferredSS13DockingPoint(GameData gameData, List<DockingPoint> otherDockingPoints, MiningShuttle shuttleRoom) {
        DockingPoint pref = null;
        for (DockingPoint dp : gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getDockingPoints()) {
            if (dp.getName().equals(playerPrefers)) {
                pref = dp;
            } else {
                if (pref == null && dp.getDescription().equals("Cargo") && dp.isVacant() && shuttleRoom.canDockAt(gameData, dp)) {
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
            MiningShuttleAction msa = new MiningShuttleAction(gameData, console);
            List<String> args = new ArrayList<>();
            args.add(event.replace("MOVE ", ""));
            msa.setActionTreeArguments(args, player);
            player.setNextAction(msa);
            readyThePlayer(gameData, player);
        } else if (event.contains("PREF")) {
            this.playerPrefers = event.replace("PREF ", "");
            buildContent(gameData, player);
        }
    }
}
