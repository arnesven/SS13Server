package model.items.weapons;

import model.Actor;
import model.GameData;

/**
 * Created by erini02 on 02/11/16.
 */
public class AutoTurretLaser extends LaserPistol {

    public AutoTurretLaser() {
        super();
        this.setName("Turret Laser");
    }

    @Override
    protected void checkOnlyMissHazard(Actor performingClient, GameData gameData) {

    }
}
