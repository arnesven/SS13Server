package model.map;

import model.GameData;
import model.map.rooms.MerchantShip;

public class CargoShuttle extends MerchantShip {
    private boolean hidden;

    public CargoShuttle(GameData gameData) {
        super(gameData);
        this.setName("Cargo Shuttle #" + getID());
    }

    public void hideYourself() {
        this.hidden = true;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }
}
