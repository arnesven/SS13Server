package model.events.ambient;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.NoPressureDamage;
import model.map.Room;

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
        for (Target t : roomRef.getTargets()) {
            t.beExposedTo(null, new AsphyxiationDamage(t));
        }
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Low Pressure";
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
