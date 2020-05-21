package model.characters.decorators;

import model.Actor;
import model.characters.general.BearCharacter;
import model.characters.general.GameCharacter;

public class BearAppearanceDecorator extends AnimalAppearanceDecorator {
    public BearAppearanceDecorator(Actor victim) {
        super(victim.getCharacter(), "Bear Appearance",
                new BearCharacter().getSprite(victim), "Bear");
    }
}
