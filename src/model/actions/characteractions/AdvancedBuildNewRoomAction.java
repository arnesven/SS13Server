package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Player;
import model.fancyframe.AdvancedBuildingFancyFrame;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.DowngoingStairsDoor;
import model.map.doors.NormalDoor;
import model.map.doors.UpgoingStairsDoor;
import model.map.rooms.CustomRoom;
import model.map.rooms.HallwayRoom;
import model.map.rooms.Room;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class AdvancedBuildNewRoomAction extends BuildNewRoomAction {

    private final AdvancedBuildingFancyFrame fancyFrame;
    private int roomX;
    private int roomY;
    private int roomZ;
    private String roomName;
    private String floors;

    public AdvancedBuildNewRoomAction(AdvancedBuildingFancyFrame advancedBuildingFancyFrame) {
        this.fancyFrame = advancedBuildingFancyFrame;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        String[] location = args.get(0).split(" ");
        roomX = Integer.parseInt(location[0]);
        roomY = Integer.parseInt(location[1]);
        roomZ = Integer.parseInt(location[2]);
        width = Integer.parseInt(args.get(1).split("x")[0]);
        height = Integer.parseInt(args.get(1).split("x")[1]);
        roomName = args.get(2);
        floors = args.get(3);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        if (fancyFrame != null) {
            fancyFrame.dispose((Player)performingClient);
        }
    }

    @Override
    protected String buildNewRoom(GameData gameData, Actor performingClient, String selected, int width, int height) throws Architecture.NoLegalPlacementForRoom {
        try {
            Architecture arch = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(performingClient.getPosition()).getName(), roomZ);
            if (arch.canRoomBeBuilt(roomX, roomY, roomZ, width, height, performingClient.getPosition())) {
                int id = gameData.getMap().getMaxID()+1;
                Room newRoom = new CustomRoom(id, roomName, "", roomX, roomY, roomZ,
                        width, height, new int[]{}, new Door[]{}, floors);


                if (roomZ == performingClient.getPosition().getZ()) {
                    Point2D doorPoint = arch.getPossibleNewDoors(newRoom).get(performingClient.getPosition());
                    newRoom.addDoor(new NormalDoor(doorPoint.getX(), doorPoint.getY(), (double) roomZ, id, performingClient.getPosition().getID()));
                } else {
                    if (roomZ > performingClient.getPosition().getZ()) {
                        newRoom.addObject(new DowngoingStairsDoor(newRoom));
                        performingClient.getPosition().addObject(new UpgoingStairsDoor(performingClient.getPosition()));
                    } else {
                        newRoom.addObject(new UpgoingStairsDoor(newRoom));
                        performingClient.getPosition().addObject(new DowngoingStairsDoor(performingClient.getPosition()));
                    }
                }
                //newRoom.setMap(gameData.getMap());
                GameMap.joinRooms(newRoom, performingClient.getPosition());
                String level = null;
                try {
                    level = gameData.getMap().getLevelForRoom(performingClient.getPosition()).getName();
                    gameData.getMap().addRoom(newRoom, level,
                            gameData.getMap().getAreaForRoom(level, performingClient.getPosition()));
                    newRoom.doSetup(gameData);
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
