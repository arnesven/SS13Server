package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

public abstract class SpellBook extends GameItem {
    public SpellBook(String string) {
        super(string, 0.5, false, 1300);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spellbook", "library.png", 2, 2, this);
    }
}
