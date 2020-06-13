package sounds;

import model.characters.special.AlienCharacter;

public class AlienSoundSet extends BarefootSoundSet {

    private final AlienCharacter chara;

    public AlienSoundSet(AlienCharacter alienCharacter) {
        this.chara = alienCharacter;
    }

    @Override
    public boolean hasDeathSound() {
        return true;
    }

    @Override
    public Sound getDeathSound() {
        return new AlienHissSound();
    }

    @Override
    public boolean hasScreamSound() {
        return true;
    }

    @Override
    public Sound getScreamSound() {
        return new AlienHissSound();
    }

    @Override
    public boolean hasLaughSound() {
        return true;
    }

    @Override
    public Sound getLaughSound() {
        return new AlienHissSound();
    }

    @Override
    public boolean hasSlowWalkingSound() {
        if (chara.getStage() == AlienCharacter.STAGE_PARASITE || chara.getStage() == AlienCharacter.STAGE_EGG) {
            return false;
        }
        return super.hasSlowWalkingSound();
    }

    @Override
    public boolean hasFastWalkingSound() {
        if (chara.getStage() == AlienCharacter.STAGE_PARASITE || chara.getStage() == AlienCharacter.STAGE_EGG) {
            return false;
        }
        return super.hasFastWalkingSound();
    }
}
