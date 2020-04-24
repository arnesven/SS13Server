package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 18/11/16.
 */
public class Crowbar extends BluntWeapon {
    public Crowbar() {
        super("Crowbar", 1.4, 20, 0.999);
    }

    @Override
    public GameItem clone() {
        return new Crowbar();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("crowbar", "items.png", 48, this);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("crowbarhandheld", "items_righthand2.png", 10, 26, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Good for opening unpowered doors" + super.getDescription(gameData, performingClient);
    }
}
