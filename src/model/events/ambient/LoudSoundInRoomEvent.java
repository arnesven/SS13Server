package model.events.ambient;

import model.actions.general.SensoryLevel;
import model.events.Event;
import sounds.Sound;

public abstract class LoudSoundInRoomEvent extends SoundInRoomEvent {

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.SCREAM;
    }
}
