package model.events;

import model.Actor;
import model.GameData;
import model.Target;
import model.map.rooms.Room;

public class FollowMovementEventForOneTurn extends FollowMovementEvent {
    public FollowMovementEventForOneTurn(Room position, Actor performingClient, Target asTarget) {
        super(position, performingClient, asTarget);
    }

    @Override
    public boolean shouldBeRemoved(GameData gameData) {
        return true;
    }
}
