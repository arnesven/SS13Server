package model;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;

import java.io.Serializable;
import java.util.List;

/**
 * Created by erini02 on 17/04/16.
 */
public abstract class Hazard implements Serializable {

    public Hazard(GameData gameData) {
        if (gameData.isRunningEvents()) {
            doHazard(gameData);
        } else {
            gameData.addEvent(new Event() {
                @Override
                public void apply(GameData gameData) {
                    doHazard(gameData);
                }

                @Override
                public String howYouAppear(Actor performingClient) {
                    return "";
                }

                @Override
                public SensoryLevel getSense() {
                    return SensoryLevel.NO_SENSE;
                }

                @Override
                public boolean shouldBeRemoved(GameData gameData) {
                    return true;
                }
            });
        }
    }

    public abstract void doHazard(GameData gameData);
}
