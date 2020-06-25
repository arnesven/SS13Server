package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.MoveTowardsPirateShipAction;
import model.actions.characteractions.SuitUpAction;
import model.actions.general.Action;
import model.characters.special.MartialArtist;
import model.items.foods.SpaceRum;
import model.items.general.GameItem;
import model.items.weapons.*;
import model.npcs.NPC;
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
    private boolean isSuitedUp;

    public PirateCharacter(int num, int startRoom, boolean isSuitedUp) {
        super("Pirate #"+num, startRoom, 7.5);
        this.isSuitedUp = isSuitedUp;
        this.weapon = MyRandom.getRandomPirateWeapon();
        this.num = num;
        Logger.log("Spawning pirate #"+num + " with " + weapon.getBaseName());
    }

    public PirateCharacter(int num, int startRoom) {
        this(num, startRoom, true);
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
    public Sprite getSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(super.getSprite(whosAsking));
        if (isSuitedUp) {
            list.add(weapon.getHandHeldSprite());
        }
        return new Sprite(super.getSprite(whosAsking).getName() + "holding" + weapon.getBaseName(),
                "human.png", 0, list, getActor());
    }

    public void setSuitedUp(boolean b) {
        this.isSuitedUp = b;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new SuitUpAction());
        if (getActor() instanceof NPC) {
            at.add(new MoveTowardsPirateShipAction());
        }
    }
}
