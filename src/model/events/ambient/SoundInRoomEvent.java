package model.events.ambient;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import sounds.Sound;

public abstract class SoundInRoomEvent extends Event {
    @Override
    public void apply(GameData gameData) {

    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.SPEECH;
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    public abstract Sound getRealSound();
}
