package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.items.suits.OxygenMask;
import model.items.suits.SpaceSuit;
import model.map.rooms.AirLockRoom;
import model.objects.general.DispenserObject;
import model.objects.general.GameObject;
import util.MyRandom;

/**
 * Created by erini02 on 09/09/17.
 */
public class OxyMaskDispenser extends DispenserObject {
    public OxyMaskDispenser(AirLockRoom airLockRoom) {
        super("EVA Storage", airLockRoom);

        if (MyRandom.nextDouble() < 0.5) {
            this.addItem(new SpaceSuit());
        }

        for (double chance = 0.6; MyRandom.nextDouble() < chance; chance /= 2.0) {
            this.addItem(new OxygenMask());
        }

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("oxymaskdispenser", "closet.png", 18);
    }
}
