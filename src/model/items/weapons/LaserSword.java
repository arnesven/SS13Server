package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.DetachAction;
import model.items.TraitorItem;
import model.items.general.GameItem;
import model.map.rooms.Room;

import java.util.ArrayList;

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
    public double doWallDamage(GameData gameData, Actor performingClient) {
        return 2.5 * super.doWallDamage(gameData, performingClient);
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

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new DetachAction(cl));
    }
}
