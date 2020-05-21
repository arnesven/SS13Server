package model.characters.decorators;

import model.Actor;
import model.characters.general.DogCharacter;
import model.characters.general.GameCharacter;

public class DogAppearanceDecorator extends AnimalAppearanceDecorator {
    public DogAppearanceDecorator(Actor victim) {
        super(victim.getCharacter(), "Dog Appearance",
                new DogCharacter().getSprite(victim), "Dog");
    }
}
