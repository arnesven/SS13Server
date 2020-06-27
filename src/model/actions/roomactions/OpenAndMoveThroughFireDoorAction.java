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
        try {
            int destinationRoom = fireDoor.getToId();
            Room to = gameData.getRoomForId(fireDoor.getToId());
            if (to == performingClient.getPosition()) {
                destinationRoom = fireDoor.getFromId();
            }
            MoveAction ma = new MoveAction(gameData, performingClient);
            List<String> args = new ArrayList<>();
            args.add(gameData.getRoomForId(destinationRoom).getName());
            ma.setArguments(args, performingClient);
            ma.doTheAction(gameData, performingClient);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }
}
