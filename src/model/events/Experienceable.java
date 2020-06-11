package model.events;

import model.Player;
import sounds.Sound;

import java.io.Serializable;

/**
 * Created by erini02 on 15/12/16.
 */
public abstract class Experienceable implements Serializable {
    public void experienceFor(Player p) {
        experienceNear(p);
        if (hasRealSound()) {
            p.getSoundQueue().add(getRealSound());
        }
    }

    protected abstract void experienceNear(Player p);


    public void experienceFromAfarFor(Player p) {
        experienceFar(p);
        if (hasRealSound()) {
            p.getSoundQueue().add(getRealSound().newWithAdjustedVolume(-20.0f));
        }
    }

    protected abstract void experienceFar(Player p);

    public boolean hasRealSound() {
        return false;
    }

    public Sound getRealSound() {
        return null;
    }
}
