package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;

/**
 * Created by erini02 on 08/12/16.
 */
public abstract class BackgroundEvent extends Event {



    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }
}
