package model.map.doors;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.animation.AnimationEvent;
import model.map.rooms.Room;

public class DoorAnimationEvent extends AnimationEvent {
    private final Door door;

    public DoorAnimationEvent(GameData gameData, Room r, Door door, Sprite sp) {
        super(gameData, r, sp);
        this.door = door;
        door.setIsAnimating(true);
    }

    public boolean hasAbsolutePosition() {
        return true;
    }

    @Override
    public SensoryLevel getSense() {
        return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE, SensoryLevel.AudioLevel.INAUDIBLE, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
    }

    @Override
    public double getAbsoluteX() {
        return door.getX();
    }

    @Override
    public double getAbsoluteY() {
        return door.getY();
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Fire Door " + door.getNumber();
    }

    @Override
    public void gotRemovedFromRoom(Room room) {
        if (door != null) {
            door.setIsAnimating(false);
        }
    }
}
