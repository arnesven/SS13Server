package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;

public class ChimpAppearanceDecorator extends AnimalAppearanceDecorator {
        public ChimpAppearanceDecorator(Actor forWhom) {
            super(forWhom.getCharacter(), "Chimp Appearance",
                    new Sprite("chimpappearance", "monkey.png", 0, forWhom), "Chimp");
        }



}
