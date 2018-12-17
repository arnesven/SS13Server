package model.characters;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;

public class MouseCharacter extends AnimalCharacter {
    public MouseCharacter(int startRoom) {
        super("Mouse", startRoom, -5.0);
    }

    @Override
    public GameCharacter clone() {
        return new MouseCharacter(getStartingRoom());
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (isDead()) {
            return new Sprite("mousedead", "animal.png", 8, 9);
        }
        return new Sprite("mouse", "animal.png", 5, 9);
    }
}
