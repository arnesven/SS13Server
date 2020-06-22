package model.npcs.behaviors;

import model.GameData;
import model.map.rooms.Room;
import model.npcs.NPC;

public class MoveToRoomNextTurn implements MovementBehavior {
    private final Room destination;
    private final int roundSetIn;
    private final GameData gameData;

    public MoveToRoomNextTurn(Room destination, GameData gameData) {
        this.destination = destination;
        this.roundSetIn = gameData.getRound();
        this.gameData = gameData;
    }

    @Override
    public void move(NPC npc) {
        if (gameData.getRound() == roundSetIn + 1) {
            npc.moveIntoRoom(destination);
        }
    }
}
