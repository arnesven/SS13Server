package model.events.ambient;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.events.damage.AsphyxiationDamage;
import model.map.rooms.Room;

/**
 * Created by erini02 on 26/04/16.
 */
public class LowPressureEvent extends Event {

    private final Room roomRef;

    public LowPressureEvent(Room roomRef) {
        this.roomRef = roomRef;
    }

    @Override
    public void apply(GameData gameData) {
        for (Target t : roomRef.getTargets(gameData)) {
            t.beExposedTo(null, new AsphyxiationDamage(t), gameData);
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("lowpressure", "screen2.png", 7, 2, this);
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Low Pressure!";
    }

    @Override
    public SensoryLevel getSense() {
        return new SensoryLevel(SensoryLevel.VisualLevel.INVISIBLE,
                SensoryLevel.AudioLevel.SAME_ROOM, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
    }

    public Room getRoom() {
        return roomRef;
    }
}
