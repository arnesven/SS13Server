package model.items.weapons;


import model.Actor;
import model.GameData;
import model.actions.general.HazardAction;
import model.events.ambient.ElectricalFire;
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
        gameData.executeAtEndOfRound(new HazardAction() {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.05) {
                    ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(performingClient.getPosition());
                    System.out.println(performingClient.getBaseName() + " started a fire!");
                }
            }
        });

    }
}
