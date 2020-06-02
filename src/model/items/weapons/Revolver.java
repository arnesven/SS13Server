package model.items.weapons;


import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Hazard;
import model.Target;
import model.events.ambient.HullBreach;
import util.Logger;
import util.MyRandom;

public class Revolver extends SlugthrowerWeapon implements PiercingWeapon {

	public Revolver() {
		super("Revolver", 0.75, 1.0, true, 1.0, 6, 199);
	}
	
	@Override
	public Revolver clone() {
		return new Revolver();
	}


    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("revolverinhand", "items_righthand.png", 12, 20, this);
    }


    @Override
    protected void checkOnlyMissHazard(final Actor performingClient, GameData gameData, Target originalTarget) {
	    super.checkOnlyMissHazard(performingClient, gameData, originalTarget);
        new Hazard(gameData) {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.20) {
                    HullBreach hull = ((HullBreach)gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(performingClient.getPosition());
                    Logger.log(Logger.INTERESTING,
                            performingClient.getBaseName() + " breached the hull with revolver!");
                }
            }
        };

    }
}
