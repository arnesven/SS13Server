package model.characters.decorators;

import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 14/04/16.
 */
public class ExtraHealthDecorator extends CharacterDecorator {

    private final int extraHP;

    public ExtraHealthDecorator(GameCharacter character, int i) {
        super(character, "Extra health");
        character.setHealth(character.getHealth() + 1);
        this.extraHP = i;
    }

    @Override
    public double getMaxHealth() {
        return super.getMaxHealth() + this.extraHP;
    }
}
