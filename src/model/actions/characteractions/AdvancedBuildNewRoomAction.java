package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.NormalDoor;
import model.map.rooms.HallwayRoom;
import model.map.rooms.Room;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class AdvancedBuildNewRoomAction extends BuildNewRoomAction {

    private int roomX;
    private int roomY;
    private int roomZ;
    private String roomName;

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        String[] location = args.get(0).split(" ");
        roomX = Integer.parseInt(location[0]);
        roomY = Integer.parseInt(location[1]);
        roomZ = Integer.parseInt(location[2]);
        width = Integer.parseInt(args.get(1).split("x")[0]);
        height = Integer.parseInt(args.get(1).split("x")[1]);
        roomName = args.get(2);
    }

    @Override
    protected String buildNewRoom(GameData gameData, Actor performingClient, String selected, int width, int height) throws Architecture.NoLegalPlacementForRoom {
        try {
            Architecture arch = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(performingClient.getPosition()).getName(), roomZ);
            if (arch.canRoomBeBuilt(roomX, roomY, roomZ, width, height, performingClient.getPosition())) {
                int id = gameData.getMap().getMaxID()+1;
                Door[] doors = new Door[]{null};
                Room newRoom = new HallwayRoom(id, roomName, "", roomX, roomY,
                        width, height, new int[]{}, doors);
                newRoom.setZ(roomZ);


                Point2D doorPoint = arch.getPossibleNewDoors(newRoom).get(performingClient.getPosition());
                doors[0] = new NormalDoor(doorPoint.getX(), doorPoint.getY(), (double)roomZ, id, performingClient.getPosition().getID());
                //newRoom.setMap(gameData.getMap());
                GameMap.joinRooms(newRoom, performingClient.getPosition());
                String level = null;
                try {
                    level = gameData.getMap().getLevelForRoom(performingClient.getPosition()).getName();
                    gameData.getMap().addRoom(newRoom, level,
                            gameData.getMap().getAreaForRoom(level, performingClient.getPosition()));
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }

            } else {
                throw new Architecture.NoLegalPlacementForRoom();
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        return roomName;
    }
}
