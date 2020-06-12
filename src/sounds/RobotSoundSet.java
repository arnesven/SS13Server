package sounds;

public class RobotSoundSet extends DefaultSoundSet {
    @Override
    public boolean hasDeathSound() {
        return true;
    }

    @Override
    public Sound getDeathSound() {
        return new Sound("borg_deathsound");
    }

    @Override
    public boolean hasSlowWalkingSound() {
        return true;
    }

    @Override
    public Sound getSlowWalkingSound() {
        return new Sound("slow_walk_robot");
    }

    @Override
    public boolean hasFastWalkingSound() {
        return true;
    }

    @Override
    public Sound getFastWalkingSound() {
        return new Sound("fast_walk_robot");
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
