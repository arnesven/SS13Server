package model.modes.objectives;

import model.GameData;
import model.items.general.Locatable;
import model.map.rooms.Room;

/**
 * Created by erini02 on 10/09/17.
 */
public class DestroyRoomObjective implements TraitorObjective {
    private final Room targetRoom;
    private final GameData gameData;

    public DestroyRoomObjective(GameData gameData, Room targetRoom) {
        this.targetRoom = targetRoom;
        this.gameData = gameData;
    }

    @Override
    public boolean wasCompleted() {
        return isCompleted(gameData);
    }

    @Override
    public int getPoints() {
        return 600;
    }

    @Override
    public Locatable getLocatable() {
        return null;
    }

    @Override
    public String getText() {
        return "Destroy the " + targetRoom.getName() + " (destroying a room requires a bomb cluster blast of at least 3).";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return targetRoom.isDestroyed();
    }
}
