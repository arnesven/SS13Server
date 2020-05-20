package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;

public class ChimpAppearanceDecorator extends CharacterDecorator {
        public ChimpAppearanceDecorator(Actor forWhom) {
            super(forWhom.getCharacter(), "Chimp Appearance");
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            Sprite sp = new Sprite("chimpappearance", "monkey.png", 0, getActor());
            if (getActor().isDead()) {
                sp.setRotation(90);
            }
            return sp;
        }

        @Override
        public String getPublicName() {
            return "Chimp";
        }

}
