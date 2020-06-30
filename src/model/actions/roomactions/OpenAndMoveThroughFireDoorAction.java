package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.map.doors.FireDoor;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class OpenAndMoveThroughFireDoorAction extends OpenFireDoorAction {
    private final FireDoor fireDoor;

    public OpenAndMoveThroughFireDoorAction(FireDoor fireDoor) {
        super(fireDoor);
        this.setName("Open and Move Through");
        this.fireDoor = fireDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        MoveAction ma = fireDoor.makeMoveThroughDoorAction(gameData, performingClient);
        ma.doTheAction(gameData, performingClient);
    }
}
