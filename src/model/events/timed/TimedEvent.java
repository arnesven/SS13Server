package model.events.timed;

import model.GameData;

import java.io.Serializable;

public abstract class TimedEvent implements Serializable {

    private final long startTimeMS;

    public TimedEvent() {
        this.startTimeMS = System.currentTimeMillis();
    }

    public abstract void apply(GameData gameData, long timedPassedMS);

    protected abstract boolean shouldBeRemoved(GameData gameData, long timePassedMS);

    public void updateYourself(GameData gameData, long timeElapsed) {
        apply(gameData, System.currentTimeMillis()-startTimeMS);
    }

    public boolean shouldBeRemoved(GameData gameData) {
        return shouldBeRemoved(gameData, System.currentTimeMillis()-startTimeMS);
    }
}
