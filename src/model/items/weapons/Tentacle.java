package model.items.weapons;

import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class Tentacle extends Weapon {
    public Tentacle() {
        super("Tentacle", 0.5, 0.5, false, 0.0, true, 0);
    }

    @Override
    public GameItem clone() {
        return new Tentacle();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "";
    }

    @Override
    public boolean hasMissSound() {
        return true;
    }
}
