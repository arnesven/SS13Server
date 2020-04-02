package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.GameMap;
import model.map.ShuttleDoor;
import model.map.rooms.Room;
import model.map.rooms.ShuttleRoom;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningShuttleAction extends ConsoleAction {

    private Room cargoBay;
    private Room miningStation;

    public MiningShuttleAction(GameData gameData) {
        super("Move Mining Shuttle", SensoryLevel.OPERATE_DEVICE);
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
                gameData.getMap().moveRoomToLevel(r, GameMap.STATION_LEVEL_NAME, "center");
                r.moveTo(cargoBay.getX()+1, cargoBay.getY()+cargoBay.getHeight(), cargoBay.getZ());
                Point2D doorPos = new Architecture(gameData.getMap(), GameMap.STATION_LEVEL_NAME).getPossibleNewDoors(r).get(cargoBay);
                GameMap.addDoor(r, new ShuttleDoor(doorPos.getX(), doorPos.getY()));
                gameData.getMap().joinRooms(r, cargoBay);
                performingClient.addTolastTurnInfo("The mining shuttle docked with SS13.");

            } else {
                gameData.getMap().moveRoomToLevel(r, "asteroid field", "mining station");
                gameData.getMap().separateRooms(r, cargoBay);
                r.moveTo(miningStation.getX(), miningStation.getY()+miningStation.getHeight(), miningStation.getZ());
                Point2D doorPos = new Architecture(gameData.getMap(), "asteroid field").getPossibleNewDoors(r).get(miningStation);
                GameMap.addDoor(r, new ShuttleDoor(doorPos.getX(), doorPos.getY()));
                gameData.getMap().joinRooms(r, miningStation);
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
