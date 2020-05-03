package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.animation.AnimatedSprite;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.Room;

public class AirLockDoor extends Door {

    private boolean isFullyOpen;
    private static AnimatedSprite closingSprite = new AnimatedSprite("closeingairlockdoor", "doors.png",
            10, 7, 32, 32, null, 6, false);
    private static AnimatedSprite openingSprite = new AnimatedSprite("openingairlockdoor", "doors.png",
            0, 9, 32, 32, null, 6, false);

    public AirLockDoor(double x, double y, double z, int fromId, int toId, boolean isOpen) {
        super(x, y, z, "Airlock", fromId, toId);
        isFullyOpen = isOpen;
    }

    public AirLockDoor(double x, double y, int fromId, int toId) {
        this(x, y, 0.0, fromId, toId, false);
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

    @Override
    protected Sprite getFogOfWarSprite() {
        return new Sprite("airlockdoor", "doors.png", 1, 7, this);
    }

    public void openAirlockDoor(GameData gameData) {
        try {
            Room from = gameData.getRoomForId(getFromId());
            Room to = gameData.getRoomForId(getToId());
            openAirlockDoor(from, to);
            from.addEvent(new OpenAirlockDoorAnimationEvent(gameData, from, this, openingSprite.copy()));
            to.addEvent(new OpenAirlockDoorAnimationEvent(gameData, to, this, openingSprite.copy()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }


    private void openAirlockDoor(Room from, Room to) {
        GameMap.joinRooms(to, from);
        isFullyOpen = true;
    }


    private void closeAirlockDoor(GameData gameData) {
        try {
            Room from = gameData.getRoomForId(getFromId());
            Room to = gameData.getRoomForId(getToId());
            closeAirlockDoor(from, to);
            from.addEvent(new CloseAirlockDoorAnimationEvent(gameData, from, this, closingSprite.copy()));
            to.addEvent(new CloseAirlockDoorAnimationEvent(gameData, to, this, closingSprite.copy()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private void closeAirlockDoor(Room from, Room to) {
        GameMap.separateRooms(to, from);
        isFullyOpen = false;
    }


    public void cycle(GameData gameData) {
        if (isFullyOpen) {
            closeAirlockDoor(gameData);
        } else {
            openAirlockDoor(gameData);
        }
    }

    public boolean isFullyOpen() {
        return isFullyOpen;
    }

}
