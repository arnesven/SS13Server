package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;
import model.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 03/09/16.
 */
public class BearCharacter extends GameCharacter {
    public BearCharacter() {
        super("Bear", 0, -3.0);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (isDead()) {
            return new Sprite("beardead", "animal.png", 5, 4, 32, 32, this);
        }
        return new Sprite("bear", "animal.png", 1, 4, 32, 32, this);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new BearCharacter();
    }

    @Override
    public Weapon getDefaultWeapon() {
        return Weapon.BEAR_CLAWS;
    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return true;
    }
}
