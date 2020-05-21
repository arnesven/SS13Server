package model.characters.decorators;

import model.Actor;
import model.characters.MouseCharacter;
import model.characters.general.GameCharacter;

public class MouseAppearanceDecorator extends AnimalAppearanceDecorator {
    public MouseAppearanceDecorator(Actor victim) {
        super(victim.getCharacter(), "Mouse Appearance",
                new MouseCharacter(0).getSprite(victim), "Mouse");
    }
}
