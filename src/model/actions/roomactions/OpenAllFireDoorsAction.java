package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.doors.Door;
import model.map.doors.ElectricalDoor;
import model.map.doors.FireDoor;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class OpenAllFireDoorsAction extends Action {
    private final Room room;
    private final ArrayList<FireDoor> targetDoors;

    public OpenAllFireDoorsAction(GameData gameData, Player forWhom, Room room) {
        super("Open All Fire Doors", SensoryLevel.OPERATE_DEVICE);
        this.room = room;
        this.targetDoors = new ArrayList<FireDoor>();
        for (Room r : gameData.getMap().getAllRoomsOnSameLevel(room)) {
            for (Door d : r.getDoors()) {
                if (d.getFromId() == room.getID() || d.getToId() == room.getID()) {
                    if (d instanceof FireDoor) {
                        targetDoors.add((FireDoor) d);
                    }
                }
            }
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "opened all fire doors";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        for (FireDoor d : targetDoors) {
            d.openFireDoor(gameData, performingClient);
        }
        performingClient.addTolastTurnInfo("You opened the fire doors to " + room.getName());
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    public int getNoOfDoors() {
        return targetDoors.size();
    }
}
