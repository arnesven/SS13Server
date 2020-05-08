package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.objects.decorations.ShuttleThruster;

public class EscapeShuttle extends ShuttleRoom {
    public EscapeShuttle(GameData gameData) {
        super(gameData.getMap().getMaxID()+1, "Escape Shuttle",
                0, 0, 3, 2, new int[]{},
                new Door[]{}, 10);
        addDecoration(new ShuttleThruster(this, 0.5));
        addDecoration(new ShuttleThruster(this, 1.5));
    }
}
