package model.items.weapons;


import graphics.sprites.Sprite;
import model.*;
import model.events.ambient.ElectricalFire;
import util.Logger;
import util.MyRandom;

public class LaserPistol extends AmmoWeapon {

	public LaserPistol() {
		super("Laser pistol", 0.90, 1.0, false, 1.0, 4, 340);
        this.setCriticalChance(0.15);
	}

	@Override
	public LaserPistol clone() {
		return new LaserPistol();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("laserpistol", "gun.png", 8);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("laserpistolinhand", "items_righthand.png", 0, 16);
    }



    @Override
    protected void checkOnlyMissHazard(final Actor performingClient, GameData gameData) {
        new Hazard(gameData) {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.05) {
                    ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(performingClient.getPosition());
                    Logger.log(Logger.INTERESTING,
                            performingClient.getBaseName() + " started a fire!");
                }
            }
        };

    }
}
