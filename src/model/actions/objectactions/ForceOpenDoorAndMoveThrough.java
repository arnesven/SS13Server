package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.map.doors.ElectricalDoor;

public class ForceOpenDoorAndMoveThrough extends ForceOpenDoorAction {
    private final ElectricalDoor door;

    public ForceOpenDoorAndMoveThrough(ElectricalDoor unpoweredDoor) {
        super(unpoweredDoor);
        setName(super.getName() + " and Move Through");
        this.door = unpoweredDoor;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        if (performingClient instanceof Player) {
            if (door.getToId() == performingClient.getPosition().getID()) {
                ((Player) performingClient).setNextMove(door.getFromId());
            } else {
                ((Player) performingClient).setNextMove(door.getToId());
            }
        }
    }
}
