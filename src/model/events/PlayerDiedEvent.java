package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import sounds.DeathSound;
import sounds.Sound;

public class PlayerDiedEvent extends Event {
    private final Actor whoDied;

    public PlayerDiedEvent(GameCharacter character) {
        super();
        this.whoDied = character.getActor();
    }

    @Override
    public boolean hasRealSound() {
        return whoDied.getCharacter().getSoundSet().hasDeathSound();
    }

    @Override
    public Sound getRealSound() {
        return whoDied.getCharacter().getSoundSet().getDeathSound();
    }

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
    public boolean shouldBeRemoved(GameData gameData) {
        return true;
    }
}
