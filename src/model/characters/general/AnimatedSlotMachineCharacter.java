package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Player;
import model.items.weapons.BluntWeapon;
import model.items.weapons.OrnateLever;
import model.items.weapons.Weapon;

/**
 * Created by erini02 on 15/12/16.
 */
public class AnimatedSlotMachineCharacter extends RobotCharacter {
    private static Weapon lever = new OrnateLever();

    public AnimatedSlotMachineCharacter(Integer id) {
        super("Slot Machine", id, 20.0);
    }


    @Override
    public Sprite getNormalSprite(Actor whosAsking) {
        return new Sprite("animatedslotmachine", "vending2.png", 7, 13, getActor());
    }

    @Override
    public Weapon getDefaultWeapon() {
        return lever;
    }
}
