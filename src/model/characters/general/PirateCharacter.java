package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.foods.SpaceRum;
import model.items.general.GameItem;
import model.items.weapons.*;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateCharacter extends HumanCharacter {
    private final int num;
    private final Weapon weapon;

    public PirateCharacter(int num, int startRoom) {
        super("Pirate #"+num, startRoom, 7.5);

        this.weapon = MyRandom.getRandomPirateWeapon();
        this.num = num;
        Logger.log("Spawning pirate #"+num + " with " + weapon.getBaseName());

    }

    @Override
    public boolean isCrew() {
        return false;
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> res = new ArrayList<>();
        res.add(weapon);
        while (MyRandom.nextDouble() < 0.5) {
            res.add(new SpaceRum());
        }
        return res;
    }

    @Override
    public GameCharacter clone() {
        return new PirateCharacter(this.num*2+1, getStartingRoom());
    }

    @Override
    public int getMovementSteps() {
        return 1;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(super.getSprite(whosAsking));
        list.add(weapon.getHandHeldSprite());
        return new Sprite(super.getSprite(whosAsking).getName() + "holding" + weapon.getBaseName(),
                "human.png", 0, list);
    }
}
