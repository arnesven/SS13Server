package model.map.rooms;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.map.doors.Door;

public class LateJoiningShuttle extends ShuttleRoom {
    public LateJoiningShuttle(GameData gameData) {
        super(gameData.getMap().getMaxID()+1, "AutoTaxi",
                0, 0, 2, 1, new int[]{},
                new Door[]{}, 1);
    }

    public static class UndockingEvent extends Event {
        private final int turnSet;
        private final LateJoiningShuttle shuttle;

        public UndockingEvent(LateJoiningShuttle lateJoiningShuttle, int turnSet) {
            super();
            this.turnSet = turnSet;
            this.shuttle = lateJoiningShuttle;
        }

        @Override
        public void apply(GameData gameData) {
            if (gameData.getRound() > turnSet) {
                shuttle.undockYourself(gameData);
                gameData.getMap().moveRoomToLevel(shuttle, "deep space", "deep space");
            }
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return null;
        }

        @Override
        public SensoryLevel getSense() {
            return null;
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return gameData.getRound() > turnSet;
        }
    }
}
