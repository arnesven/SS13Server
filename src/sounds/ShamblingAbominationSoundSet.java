package sounds;

public class ShamblingAbominationSoundSet extends BarefootSoundSet {

    @Override
    public boolean hasScreamSound() {
        return true;
    }

    @Override
    public Sound getScreamSound() {
        return new Sound("ling_roar");
    }

    @Override
    public boolean hasDeathSound() {
        return true;
    }

    @Override
    public Sound getDeathSound() {
        return new MaleDeathSound();
    }
}
