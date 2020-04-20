package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.ActionOption;
import model.map.rooms.Room;

import java.util.List;

public class MoveToSpecificRoomAction extends MoveAction {
    private final Room room;

    public MoveToSpecificRoomAction(GameData gameData, Player forWhom, Room room) {
        super(gameData, forWhom);
        setName("Move to " + room.getName());
        this.room = room;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        return new ActionOption(getName());
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        setDestination(room);
    }
}
