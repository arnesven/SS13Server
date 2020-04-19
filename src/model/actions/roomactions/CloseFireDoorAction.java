package model.actions.roomactions;

import graphics.ClientInfo;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.items.NoSuchThingException;
import model.map.doors.FireDoor;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.ElectricalDoor;
import model.map.rooms.Room;

import java.util.List;

public class CloseFireDoorAction extends Action {
    private final ElectricalDoor door;
    private FireDoor theFireDoor;

    public CloseFireDoorAction(ElectricalDoor electricalDoor) {
        super("Close Fire Door " + electricalDoor.getNumber(), SensoryLevel.PHYSICAL_ACTIVITY);
        this.door = electricalDoor;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "closed a fire door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
            door.shutFireDoor(gameData, performingClient);
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }



}
