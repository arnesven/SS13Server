package sounds;

import model.characters.general.HumanCharacter;
import util.MyRandom;

public class HumanSoundSet extends DefaultSoundSet {

    private static final Sound SLOW_WALK = new Sound("slow_walk_floor");
    private static final Sound FAST_WALK = new Sound("fast_walk_floor");
    private final HumanCharacter character;

    public HumanSoundSet(HumanCharacter humanCharacter) {
        this.character = humanCharacter;
    }

    @Override
    public boolean hasDeathSound() {
        return true;
    }

    @Override
    public Sound getDeathSound() {
        if (character.getGender().equals("man")) {
            return new MaleDeathSound();
        }
        return new Sound("femalescream_4");
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

    @Override
    public boolean hasScreamSound() {
        return true;
    }

    @Override
    public Sound getScreamSound() {
        return makeScreamSound(character.getGender());
    }


    private static Sound makeScreamSound(String gender) {
        if (gender.equals("man")) {
            return new Sound("malescream_" + (MyRandom.nextInt(6)+1));
        }
        return new Sound("femalescream_" + (MyRandom.nextInt(5)+1));
    }

    @Override
    public boolean hasLaughSound() {
        return true;
    }

    @Override
    public Sound getLaughSound() {
        if (character.getGender().equals("man")) {
            return new Sound("manlaugh" + (MyRandom.nextInt(2) + 1));
        }
        return new Sound("womanlaugh");
    }
}
