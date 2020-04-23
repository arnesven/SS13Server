package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.FireDoor;
import model.map.doors.NormalDoor;
import model.map.rooms.Room;

import java.util.List;

public class OpenFireDoorAction extends Action {
    private final FireDoor door;

    public OpenFireDoorAction(FireDoor fireDoor) {
        super("Open Fire Door", SensoryLevel.PHYSICAL_ACTIVITY);
        this.door = fireDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "opened a fire door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        door.openFireDoor(gameData, performingClient);
        String ending = "what, no fire in there?";
        try {
            if (gameData.getRoomForId(door.getToId()).hasFire()) {
                ending = " it's burning in there!";
            }
            performingClient.addTolastTurnInfo("You opened the fire door, " + ending);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
