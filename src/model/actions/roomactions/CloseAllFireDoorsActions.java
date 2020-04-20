package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.doors.Door;
import model.map.doors.ElectricalDoor;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class CloseAllFireDoorsActions extends Action {
    private final Room room;
    private final ArrayList<ElectricalDoor> targetDoors;

    public CloseAllFireDoorsActions(GameData gameData, Player forWhom, Room room) {
        super("Close Fire Doors", SensoryLevel.OPERATE_DEVICE);
        this.room = room;
        this.targetDoors = new ArrayList<ElectricalDoor>();
        for (Room r : gameData.getMap().getAllRoomsOnSameLevel(room)) {
            for (Door d : r.getDoors()) {
                if (d.getFromId() == room.getID() || d.getToId() == room.getID()) {
                    if (d instanceof ElectricalDoor) {
                        targetDoors.add((ElectricalDoor) d);
                    }
                }
            }
        }
    }

    public int getNoOfDoors() {
        return targetDoors.size();
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "closed all the fire doors";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        for (ElectricalDoor d : targetDoors) {
            d.shutFireDoor(gameData, performingClient);
        }
        performingClient.addTolastTurnInfo("You closed the fire doors to " + room.getName());
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
