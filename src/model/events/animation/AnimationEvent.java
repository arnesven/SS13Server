package model.events.animation;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.general.Grenade;
import model.map.rooms.Room;

public class AnimationEvent extends Event {
    private final SpriteObject obj;
    private final int roundCreated;
    private final Room room;
    private final Sprite sprite;

    public AnimationEvent(SpriteObject obj, GameData gameData, Room r, Sprite sp) {
        this.obj = obj;
        this.roundCreated = gameData.getRound();
        gameData.addEvent(this);
        this.room = r;
        this.sprite = sp;
    }

    @Override
    public void apply(GameData gameData) {
        if (gameData.getRound() > roundCreated) {
            room.removeEvent(this);
        }
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return null;
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.EXPLOSION;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return sprite;
    }

    @Override
    public boolean shouldBeRemoved(GameData gameData) {
        return gameData.getRound() > roundCreated;
    }
}
