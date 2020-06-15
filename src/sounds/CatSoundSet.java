package sounds;

public class CatSoundSet extends BarefootSoundSet {

    @Override
    public boolean hasScreamSound() {
        return true;
    }

    @Override
    public Sound getScreamSound() {
        return new Sound("cat-hissing");
    }

    @Override
    public boolean hasDeathSound() {
        return true;
    }

    @Override
    public Sound getDeathSound() {
        return new Sound("meow1");
    }
}
