package model.events.ambient;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.damage.RadiationDamage;
import model.map.Room;

/**
 * Created by erini02 on 19/04/16.
 */
public class LocalRadiationEvent extends RadiationStorm {

    private final Room position;
    private final Actor causedBy;

    public LocalRadiationEvent(Room pos, Actor causedBy) {
        this.position = pos;
        this.causedBy = causedBy;
    }

    @Override
    public void apply(GameData gameData) {
        for (Actor a : this.position.getActors()) {
            a.getAsTarget().beExposedTo(causedBy, new RadiationDamage(0.5));
        }
    }

    @Override
    public boolean shouldBeRemoved(GameData gameData) {
        return true;
    }
}
