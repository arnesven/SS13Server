package model.items.weapons;

import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class Probocis extends Weapon {
    public Probocis() {
        super("Proboscis", 0.95, 1.0, false, 0.0, 0);
    }

    @Override
    public GameItem clone() {
        return new Probocis();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "";
    }
}
