package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.Room;
import model.map.rooms.MiningShuttle;
import model.objects.consoles.MiningShuttleControl;
import util.Logger;

import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningShuttleAction extends ConsoleAction {

    private final MiningShuttleControl shuttleControl;
    private final GameData gameData;
    private MiningShuttle shuttle;
    private Room cargoBay;
    private Room miningStation;
    private DockingPoint selectedDockingPoint;

    public MiningShuttleAction(GameData gameData, MiningShuttleControl shuttleControl) {
        super("Move Mining Shuttle", SensoryLevel.OPERATE_DEVICE);
        this.shuttleControl = shuttleControl;
        this.gameData = gameData;
        try {
            cargoBay = gameData.getMap().getRoom("Cargo Bay");
            miningStation = gameData.getMap().getRoom("Mining Station");
            shuttle = (MiningShuttle)gameData.getMap().getRoom("Mining Shuttle");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Shuttle Control";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        try {
            if (gameData.getMap().getLevelForRoom(shuttle).getName().equals(GameMap.STATION_LEVEL_NAME)) {
                for (DockingPoint dp : gameData.getMap().getLevelForRoom(miningStation).getDockingPoints()) {
                    if (shuttle.canDockAt(gameData, dp)) {
                        opts.addOption(dp.getName());
                    }
                }
            } else {
                for (DockingPoint dp : gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getDockingPoints()) {
                    if (shuttle.canDockAt(gameData, dp)) {
                        opts.addOption(dp.getName());
                    }
                }
            }

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            MiningShuttle r = (MiningShuttle)gameData.getMap().getRoom("Mining Shuttle");
            if (gameData.getMap().getLevelForRoom(r).getName().equals("asteroid field")) {
                r.undockYourself(gameData);
                gameData.getMap().moveRoomToLevel(r, GameMap.STATION_LEVEL_NAME, "center");
                r.dockYourself(gameData, selectedDockingPoint);
                performingClient.addTolastTurnInfo("The mining shuttle docked with SS13 at " + selectedDockingPoint.getName() + ".");
            } else {
                r.undockYourself(gameData);
                gameData.getMap().moveRoomToLevel(r, "asteroid field", "mining station");
                r.dockYourself(gameData, selectedDockingPoint);
                performingClient.addTolastTurnInfo("The mining shuttle docked with the mining station.");
            }


        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        this.selectedDockingPoint = null;
        try {
            for (DockingPoint dp : gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getDockingPoints()) {
                if (args.get(0).equals(dp.getName())) {
                    selectedDockingPoint = dp;
                    return;
                }
            }
            for (DockingPoint dp : gameData.getMap().getLevelForRoom(miningStation).getDockingPoints()) {
                if (args.get(0).equals(dp.getName())) {
                    selectedDockingPoint = dp;
                    return;
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        Logger.log("WARNING! No selected docking point found!");

    }
}
