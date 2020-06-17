package model.items.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import sounds.Sound;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningDrill extends Weapon {
    public MiningDrill() {
        super("Mining Drill", 0.75, 1.0, false, 1.0, true, 200);
        setCriticalChance(0.10);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("miningdrill", "equipment.png", 6, 1, this);
    }

    @Override
    public GameItem clone() {
        return new MiningDrill();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A powerful drill which can drill into the hardest regolith rock (or walls). Used by asteroid miners.";
    }

    @Override
    public double doWallDamage(GameData gameData, Actor performingClient) {
        return 2.5*super.doWallDamage(gameData, performingClient);
    }

    @Override
    public String getWallDamageText() {
        return "<b>RRRRRRrrrrr!</b>Sounds like someone is drilling through the wall!";
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("drill");
    }

    @Override
    public boolean isMeleeWeapon() {
        return super.isMeleeWeapon();
    }
}
