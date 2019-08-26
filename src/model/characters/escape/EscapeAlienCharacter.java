package model.characters.escape;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class EscapeAlienCharacter extends GameCharacter {
    public EscapeAlienCharacter() {
        super("Alien", 2, 20.0);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("escapealien", "alien.png", 0, getActor());
    }

    @Override
    public GameCharacter clone() {
        return new EscapeAlienCharacter();
    }
}
