package model.characters;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.SqueakAction;
import model.actions.general.Action;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;
import model.items.weapons.Weapon;

import java.util.ArrayList;

public class MouseCharacter extends AnimalCharacter {
    public MouseCharacter(int startRoom) {
        super("Mouse", startRoom, -5.0);
    }

    @Override
    public GameCharacter clone() {
        return new MouseCharacter(getStartingRoom());
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new SqueakAction());
    }

    @Override
    public Weapon getDefaultWeapon() {
        return Weapon.TEETH;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (isDead()) {
            return new Sprite("mousedead", "animal.png", 8, 9, getActor());
        }
        return new Sprite("mouse", "animal.png", 5, 9, getActor());
    }
}
