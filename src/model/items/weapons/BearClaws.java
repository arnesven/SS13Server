package model.items.weapons;

import model.GameData;
import model.Player;
import model.items.general.GameItem;

/**
 * Created by erini02 on 06/09/17.
 */
public class BearClaws extends SlashingWeapon {
    public BearClaws() {
        super("Bear claws", 0.5, 1.0, false, -1.0, true, 0);
    }

    @Override
    public GameItem clone() {
        return new BearClaws();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "This is not the edible kind.";
    }
}
