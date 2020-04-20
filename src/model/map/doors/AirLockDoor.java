package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.Room;

public class AirLockDoor extends Door {

    private boolean isFullyOpen;

    public AirLockDoor(double x, double y, int fromId, int toId, boolean isOpen) {
        super(x, y, "Airlock", fromId, toId);
        isFullyOpen = isOpen;
    }

    public AirLockDoor(double x, double y, int fromId, int toId) {
        this(x, y, fromId, toId, false);
    }

    @Override
    protected Sprite getSprite() {
        if (isAnimating()) {
            return Sprite.blankSprite();
        } else if (isFullyOpen) {
            return new Sprite("fullyopenairlockdoor", "doors.png", 5, 9, this);
        }
        return new Sprite("airlockdoor", "doors.png", 1, 7, this);
    }

    public void openAirlockDoor(GameData gameData, Actor performingClient) {
        try {
            Room from = gameData.getRoomForId(getFromId());
            Room to = gameData.getRoomForId(getToId());
            openAirlockDoor(from, to);
            from.addEvent(new OpenAirlockDoorAnimationEvent(gameData, from, this));
            to.addEvent(new OpenAirlockDoorAnimationEvent(gameData, to, this));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }


    private void openAirlockDoor(Room from, Room to) {
        GameMap.joinRooms(to, from);
        isFullyOpen = true;
    }


    private void closeAirlockDoor(GameData gameData, Actor performingClient) {
        try {
            Room from = gameData.getRoomForId(getFromId());
            Room to = gameData.getRoomForId(getToId());
            closeAirlockDoor(from, to);
            from.addEvent(new CloseAirlockDoorAnimationEvent(gameData, from, this));
            to.addEvent(new CloseAirlockDoorAnimationEvent(gameData, to, this));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private void closeAirlockDoor(Room from, Room to) {
        GameMap.separateRooms(to, from);
        isFullyOpen = false;
    }


    public void cycle(GameData gameData, Actor performingClient) {
        if (isFullyOpen) {
            closeAirlockDoor(gameData, performingClient);
        } else {
            openAirlockDoor(gameData, performingClient);
        }
    }

}
