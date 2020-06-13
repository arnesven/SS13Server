package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import util.Logger;

import java.util.*;

/**
 * Created by erini02 on 18/10/16.
 */
public class EyeballAlienCharacter extends GameCharacter {

    private static Set<Actor> angryAt = new HashSet<>();
    private static int num = 1;

     public EyeballAlienCharacter() {
        super("Eyeball Alien #" + (num++), 0, 6.666);
    }

    public static Set<Actor> getAngryAt() {
        return angryAt;
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public String getPublicName() {
		String res = getBaseName();
		return res;
	}

    @Override
    public GameCharacter clone() {
        return new EyeballAlienCharacter();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (isDead()) {
            return new Sprite("eyeballaliendead", "weapons2.png", 44, 7, 32, 32, getActor());
        }
        return new Sprite("eyeballalien", "weapons2.png", 46, 5, 32, 32, getActor());
    }

    @Override
    public Weapon getDefaultWeapon() {
        return Weapon.TENTACLE;
    }
    

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new MitosisAction());

    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return true;
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
        boolean result = super.beAttackedBy(performingClient, weapon, gameData);
        if (result) {
            angryAt.add(performingClient);
            Logger.log("Eyeball aliens are now angry at " + performingClient.getBaseName() + "!");
        }
        return result;
    }
}
