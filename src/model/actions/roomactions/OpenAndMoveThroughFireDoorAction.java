package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.map.doors.FireDoor;
import model.map.rooms.Room;

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
            if (performingClient instanceof Player) {
                ((Player) performingClient).setNextMove(destinationRoom);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }
}
