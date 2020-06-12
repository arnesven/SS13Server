package sounds;

import java.io.Serializable;

public interface SoundSet extends Serializable {
    boolean hasDeathSound();
    Sound getDeathSound();

    boolean hasSlowWalkingSound();
    Sound getSlowWalkingSound();

    boolean hasFastWalkingSound();
    Sound getFastWalkingSound();
}
