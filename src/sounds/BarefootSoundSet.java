package sounds;

public class BarefootSoundSet implements SoundSet {

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
        return true;
    }

    @Override
    public Sound getSlowWalkingSound() {
        return new Sound("barefoot_walk_slow");
    }

    @Override
    public boolean hasFastWalkingSound() {
        return true;
    }

    @Override
    public Sound getFastWalkingSound() {
        return new Sound("barefoot_walk_fast");
    }

    @Override
    public boolean hasScreamSound() {
        return true;
    }

    @Override
    public Sound getScreamSound() {
        return null;
    }

    @Override
    public boolean hasLaughSound() {
        return false;
    }

    @Override
    public Sound getLaughSound() {
        return null;
    }

    @Override
    public boolean hasAmbientSound() {
        return false;
    }

    @Override
    public String getAmbientSound() {
        return null;
    }
}
