package model.items.weapons;


import model.Actor;
import model.GameData;
import model.actions.general.HazardAction;
import model.events.ambient.HullBreach;
import util.MyRandom;

public class Revolver extends AmmoWeapon {

	public Revolver() {
		super("Revolver", 0.75, 1.0, true, 1.0, 6);
	}
	
	@Override
	public Revolver clone() {
		return new Revolver();
	}

    @Override
    protected void checkOnlyMissHazard(final Actor performingClient, GameData gameData) {
        gameData.executeAtEndOfRound(new HazardAction() {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.20) {
                    HullBreach hull = ((HullBreach)gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(performingClient.getPosition());
                    System.out.println(performingClient.getBaseName() + " breached the hull with revolver!");
                }
            }
        });

    }
}
