package model.map.rooms;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.doors.Door;

import java.util.ArrayList;
import java.util.List;

public abstract class DecorativeRoom extends Room {
    public DecorativeRoom(int ID, String name, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public List<Action> getActionData(GameData gameData, Player forWhom) {
        return new ArrayList<>();
    }

    @Override
    public boolean isPartOfStation() {
        return true;
    }

    @Override
    public boolean shouldBeAddedToMinimap() {
        return false;
    }
}
