package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.npcs.animals.AnimalNPC;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 27/11/16.
 */
public class MutatedTomatoCharacter extends AnimalCharacter {
    public MutatedTomatoCharacter(int startRoom) {
        super("Mutated Tomato", startRoom, -7.5);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("mutatedtomato", "animal.png", 22, 5, this);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new MutatedTomatoCharacter(getStartingRoom());
    }

    @Override
    public Weapon getDefaultWeapon() {
        return Weapon.TEETH;
    }
}
