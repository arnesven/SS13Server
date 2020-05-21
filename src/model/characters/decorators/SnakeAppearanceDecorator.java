package model.characters.decorators;

import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.general.SnakeCharacter;

public class SnakeAppearanceDecorator extends AnimalAppearanceDecorator {
    public SnakeAppearanceDecorator(Actor victim) {
        super(victim.getCharacter(), "Snake Appearance", new SnakeCharacter(0).getSprite(victim), "Snake");
    }
}
