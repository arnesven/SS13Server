package sounds;

public class HumanSoundSet implements SoundSet {

    private static final Sound SLOW_WALK = new Sound("slow_walk_floor");
    private static final Sound FAST_WALK = new Sound("fast_walk_floor");

    @Override
    public boolean hasDeathSound() {
        return true;
    }

    @Override
    public Sound getDeathSound() {
        return new DeathSound();
    }

    @Override
    public boolean hasSlowWalkingSound() {
        return true;
    }

    @Override
    public Sound getSlowWalkingSound() {
        return SLOW_WALK;
    }

    @Override
    public boolean hasFastWalkingSound() {
        return true;
    }

    @Override
    public Sound getFastWalkingSound() {
        return FAST_WALK;
    }
}
