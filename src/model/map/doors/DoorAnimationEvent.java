package model.map.doors;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.animation.AnimationEvent;
import model.map.rooms.Room;
import util.Logger;

public class DoorAnimationEvent extends AnimationEvent {
    private final Door door;

    public DoorAnimationEvent(GameData gameData, Room r, Door door, Sprite sp) {
        super(gameData, r, sp, "Door Animation");
        this.door = door;
        door.setIsAnimating(true);
    }

    public boolean hasAbsolutePosition() {
        return true;
    }

    @Override
    public SensoryLevel getSense() {
        return new SensoryLevel(SensoryLevel.VisualLevel.VISIBLE_IF_CLOSE, SensoryLevel.AudioLevel.INAUDIBLE, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
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
    public double getAbsoluteZ() {
        return door.getZ();
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return door.getName();
    }

    @Override
    public void gotRemovedFromRoom(Room room) {
        if (door != null) {
            door.setIsAnimating(false);
        }
    }
}
