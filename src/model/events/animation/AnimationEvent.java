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
    private final int roundCreated;
    private final Room room;
    private final Sprite sprite;
    private final String label;

    public AnimationEvent(GameData gameData, Room r, Sprite sp, String label) {
        this.roundCreated = gameData.getRound();
        gameData.addEvent(this);
        this.room = r;
        this.sprite = sp;
        this.sprite.setObjectRef(this);
        this.label = label;
    }

    @Override
    public void apply(GameData gameData) {
        if (gameData.getRound() > roundCreated) {
            room.removeEvent(this);
        }
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return label;
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
