package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.map.rooms.Room;
import model.npcs.NPC;

import java.util.ArrayList;
import java.util.List;

public class GoTowardsSpecificActorMovement extends GoTowardsRoomMovement {
    private final Actor target;

    public GoTowardsSpecificActorMovement(GameData gameData, Actor whosAsking) {
        super(gameData);
        this.target = whosAsking;
    }

    @Override
    protected List<Room> getEligableRooms(NPC npc, GameData gameData) {
        List<Room> list = new ArrayList<>();
        list.add(target.getPosition());
        return list;
    }
}
