package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.TraitorItem;
import model.items.general.GameItem;
import model.map.rooms.Room;

/**
 * Created by erini02 on 26/04/16.
 */
public class LaserSword extends SlashingWeapon implements TraitorItem {
    public LaserSword() {
        super("Laser Sword", 0.9, 1.0, false, 0.5, true, 399);
        this.setCriticalChance(0.15);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("laserswordinhand", "items_righthand.png", 37, 37, this);
    }

    @Override
    public GameItem clone() {
        return new LaserSword();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("lasersword", "weapons.png", 1, this);
    }

    @Override
    public double doWallDamage(GameData gameData, Actor performingClient, Room targetRoom) {
        return 2.5 * super.doWallDamage(gameData, performingClient, targetRoom);
    }

    @Override
    public int getTelecrystalCost() {
        return 6;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An elegant weapon, for a more civilized age. Cuts through stuff like it was butter.";
    }

    @Override
    public String getWallDamageText() {
        return "A part of the wall is starting to glow faintly...";
    }
}
