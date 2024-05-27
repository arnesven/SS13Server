package model.items.weapons;

import model.GameData;
import model.Player;
import model.items.general.GameItem;

public class Tusks extends Weapon implements PiercingWeapon {
    public Tusks() {
        super("Tusks", 0.5, 1.0, false, 1.0, true, 0);
    }

    @Override
    public GameItem clone() {
        return new Tusks();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "";
    }
}
