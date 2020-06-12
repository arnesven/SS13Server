package sounds;

public class DefaultSoundSet implements SoundSet {
    @Override
    public boolean hasDeathSound() {
        return false;
    }

    @Override
    public Sound getDeathSound() {
        return null;
    }

    @Override
    public boolean hasSlowWalkingSound() {
        return false;
    }

    @Override
    public Sound getSlowWalkingSound() {
        return null;
    }

    @Override
    public boolean hasFastWalkingSound() {
        return false;
    }

    @Override
    public Sound getFastWalkingSound() {
        return null;
    }

    @Override
    public boolean hasScreamSound() {
        return false;
    }

    @Override
    public Sound getScreamSound() {
        return null;
    }
}
