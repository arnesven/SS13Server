package model.items.weapons;

import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 17/11/16.
 */
public class ShotgunShells extends Ammunition {
    public ShotgunShells() {
        super("Shotgun Shells", 0.2, 10);
    }

    @Override
    public boolean canBeLoadedIntoGun(AmmoWeapon w) {
        return w instanceof Shotgun;
    }

    @Override
    public void loadIntoGun(AmmoWeapon selected) {
        selected.setShots(selected.getMaxShots());
    }

    @Override
    public GameItem clone() {
        return new ShotgunShells();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Ammunition for a shotgun.";
    }
}
