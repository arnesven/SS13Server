package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.GameMap;
import model.map.ShuttleDoor;
import model.map.doors.Door;
import model.map.rooms.Room;
import model.map.rooms.ShuttleRoom;
import model.objects.consoles.ShuttleControl;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningShuttleAction extends ConsoleAction {

    private final ShuttleControl shuttleControl;
    private Room cargoBay;
    private Room miningStation;

    public MiningShuttleAction(GameData gameData, ShuttleControl shuttleControl) {
        super("Move Mining Shuttle", SensoryLevel.OPERATE_DEVICE);
        this.shuttleControl = shuttleControl;
        try {
            cargoBay = gameData.getMap().getRoom("Cargo Bay");
            miningStation = gameData.getMap().getRoom("Mining Station");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Shuttle Control";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            ShuttleRoom r = (ShuttleRoom)gameData.getMap().getRoom("Mining Shuttle");
            if (gameData.getMap().getLevelForRoom(r).getName().equals("asteroid field")) {
                gameData.getMap().separateRooms(r, miningStation);
                if (shuttleControl.getOldDoor() != null) {
                    r.removeDoor(shuttleControl.getOldDoor());
                }
                gameData.getMap().moveRoomToLevel(r, GameMap.STATION_LEVEL_NAME, "center");
                r.moveTo(cargoBay.getX()+1, cargoBay.getY()+cargoBay.getHeight(), cargoBay.getZ());
                Architecture stationArch = new Architecture(gameData.getMap(), GameMap.STATION_LEVEL_NAME);
                Door oldDoor =  new ShuttleDoor(0.0, 0.0, cargoBay.getID(), r.getID());
                stationArch.joinRoomsWithDoor(r, cargoBay, oldDoor);
                shuttleControl.setOldDoor(oldDoor);

                performingClient.addTolastTurnInfo("The mining shuttle docked with SS13.");

            } else {
                gameData.getMap().separateRooms(r, cargoBay);
                if (shuttleControl.getOldDoor() != null) {
                    r.removeDoor(shuttleControl.getOldDoor());
                }
                gameData.getMap().moveRoomToLevel(r, "asteroid field", "mining station");
                r.moveTo(miningStation.getX(), miningStation.getY()+miningStation.getHeight(), miningStation.getZ());
                Architecture asteroidFieldArch = new Architecture(gameData.getMap(), "asteroid field");
                Door oldDoor = new ShuttleDoor(0.0, 0.0, miningStation.getID(), r.getID());
                asteroidFieldArch.joinRoomsWithDoor(r, miningStation, oldDoor);
                shuttleControl.setOldDoor(oldDoor);

                performingClient.addTolastTurnInfo("The mining shuttle docked with the mining station.");
            }


        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
