package model.items.weapons;


import model.Actor;
import model.GameData;
import model.Hazard;
import model.events.ambient.ElectricalFire;
import util.Logger;
import util.MyRandom;

public class LaserPistol extends AmmoWeapon {

	public LaserPistol() {
		super("Laser pistol", 0.90, 1.0, false, 1.0, 4);
        this.setCriticalChance(0.15);
	}

	@Override
	public LaserPistol clone() {
		return new LaserPistol();
	}
	
	@Override
	protected char getIcon() {
		return 'L';
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
