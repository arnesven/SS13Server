package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.general.GameItem;
import model.items.weapons.Weapon;

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

    @Override
    public Weapon getDefaultWeapon() {
        return Weapon.TENTACLE;
    }

//    @Override
//    public boolean isCrew() {
//        return false;
//    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new MitosisAction());

    }
}
