package model.mutations;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.weapons.Weapon;

import java.util.ArrayList;

/**
 * Created by erini02 on 03/09/16.
 */
public class ShootElectricity extends Mutation {
    public ShootElectricity() {
        super("Shoot Electricity");
    }

    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new CharacterDecorator(forWhom.getCharacter(), "Shoot Electricity") {
            @Override
            public Weapon getDefaultWeapon() {
                return new Weapon("Electric shock", 0.9, 1.0, false, 0.0, false);
            }
        };
    }
}
