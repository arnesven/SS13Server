package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;

public class RevolverAmmo extends Ammunition {
    public RevolverAmmo() {
        super("Revolver Ammo", 0.2, 12);
    }

    @Override
    public boolean canBeLoadedIntoGun(AmmoWeapon w) {
        return w instanceof Revolver;
    }

    @Override
    public void loadIntoGun(AmmoWeapon selected) {
        selected.setShots(selected.getMaxShots());
    }

    @Override
    public GameItem clone() {
        return new RevolverAmmo();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("revammo", "ammo.png", 1, 5, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Ammunition for a revolver.";
    }

}
