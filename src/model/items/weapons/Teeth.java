package model.items.weapons;

import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class Teeth extends Weapon implements PiercingWeapon {
    public Teeth() {
        super("Teeth", 0.5, 0.5, false, 0.0, false, 0);
    }

    @Override
    public GameItem clone() {
        return new Teeth();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A set of teeth.";
    }
}
