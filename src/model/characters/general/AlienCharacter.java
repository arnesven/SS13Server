package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/10/16.
 */
public class AlienCharacter extends GameCharacter {
    public AlienCharacter() {
        super("Eyeball Alien", 0, 6.666);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new AlienCharacter();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("eyeballalien", "weapons2.png", 46, 5, 32, 32);
    }
}
