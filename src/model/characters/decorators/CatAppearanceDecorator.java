package model.characters.decorators;

import model.Actor;
import model.characters.general.CatCharacter;
import model.characters.general.GameCharacter;

public class CatAppearanceDecorator extends AnimalAppearanceDecorator {
    public CatAppearanceDecorator(Actor victim) {
        super(victim.getCharacter(), "Cat Appearance", new CatCharacter().getSprite(victim), "Cat");
    }
}
