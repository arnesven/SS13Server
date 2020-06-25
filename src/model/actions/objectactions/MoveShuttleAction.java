package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.MiningShuttle;
import model.map.rooms.Room;
import model.map.rooms.ShuttleRoom;
import model.objects.consoles.MiningShuttleControl;
import model.objects.consoles.ShuttleControlConsole;
import util.Logger;

import java.util.List;

public abstract class MoveShuttleAction extends ConsoleAction {
    private final ShuttleRoom shuttle;
    private final String levelA;
    private final String levelB;
    private final ShuttleControlConsole shuttleControl;
    private final GameData gameData;
    private DockingPoint selectedDockingPoint;


    public MoveShuttleAction(String name, GameData gameData, ShuttleControlConsole shuttleControl) {
        super(name, SensoryLevel.OPERATE_DEVICE);
        this.levelA = shuttleControl.getLevelA();
        this.levelB = shuttleControl.getLevelB();
        this.gameData = gameData;
        this.shuttleControl = shuttleControl;
        try {
            this.shuttle = (ShuttleRoom)gameData.getRoom(shuttleControl.getShuttleName());
        } catch (NoSuchThingException e) {
            throw new IllegalStateException("Could not find shuttle " + shuttleControl.getShuttleName());
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
            if (gameData.getMap().getLevelForRoom(shuttle).getName().equals(levelA)) {
                for (DockingPoint dp : gameData.getMap().getLevel(levelB).getDockingPoints()) {
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
    public void setArguments(List<String> args, Actor performingClient) {
        this.selectedDockingPoint = null;
        for (DockingPoint dp : gameData.getMap().getLevel(levelA).getDockingPoints()) {
            if (args.get(0).equals(dp.getName())) {
                selectedDockingPoint = dp;
                return;
            }
        }
        for (DockingPoint dp : gameData.getMap().getLevel(levelB).getDockingPoints()) {
            if (args.get(0).equals(dp.getName())) {
                selectedDockingPoint = dp;
                return;
            }
        }

        Logger.log("WARNING! No selected docking point found!");
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            if (gameData.getMap().getLevelForRoom(shuttle).getName().equals(levelB)) {
                shuttle.undockYourself(gameData);
                gameData.getMap().moveRoomToLevel(shuttle, levelA, "somewhere");
                shuttle.dockYourself(gameData, selectedDockingPoint);
                performingClient.addTolastTurnInfo("The " + shuttle.getName().toLowerCase() + " docked with " + levelA
                                                        + " at " + selectedDockingPoint.getName() + ".");
            } else {
                shuttle.undockYourself(gameData);
                gameData.getMap().moveRoomToLevel(shuttle, levelB, "somewhere");
                shuttle.dockYourself(gameData, selectedDockingPoint);
                performingClient.addTolastTurnInfo("The " + shuttle.getName().toLowerCase() + " docked with " + levelB +
                                                     " at " + selectedDockingPoint.getName() + ".");
            }


        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }


}
