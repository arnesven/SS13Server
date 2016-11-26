package model.events.ambient;

import model.events.Event;
import model.items.NoSuchThingException;
import model.objects.consoles.PowerSource;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;

public abstract class PowerFlux extends AmbientEvent {

	private static final double FLUX_SUSTAIN_FACTOR = 0.2; // maximum sustained power flux is 30% of normal power (per turn)
	private static final double FLUX_FIXED_FACTOR = 0.4;   // maximum power spike/drain is 80% of normal power
	private static final double occurranceChance = 0.08;
    private boolean sustaining = false;
	private boolean increasing = false;

    @Override
    protected double getStaticProbability() {
        return occurranceChance;
    }

    protected abstract PowerSource findePowerSource(GameData gameData) throws NoSuchThingException;


    @Override
	public void apply(GameData gameData) {
        Logger.log("Applying Power Flux");
        PowerSource gc;
        try {
            gc = findePowerSource(gameData);
        } catch (NoSuchThingException e) {
            Logger.log("Cannot find generator, PowerFlux has no effect.");
            return;
        }
        if (sustaining ) {
			
			gc.addToLevel(randomAmount(FLUX_SUSTAIN_FACTOR, gc) * (increasing ?1.0:-1.0));
			if (MyRandom.nextDouble() < 0.33 || gc.getPowerLevel() == 0.0) {
				Logger.log(Logger.INTERESTING,
                        "Sustained power flux over");
				sustaining = false;
			} else {
                Logger.log("Sustaining flux");
            }
			
		} else if (MyRandom.nextDouble() < getProbability()) {
			if (MyRandom.nextDouble() < 0.5) {
				sustaining = true;
				increasing = MyRandom.nextDouble() < 0.2;
                Logger.log(Logger.INTERESTING,
                        "Sustained " + (increasing?"positive":"negative") +
								   " power flux began");
			} else {
				double amount;
				if (MyRandom.nextDouble() < 0.3) {
					amount = randomAmount(FLUX_FIXED_FACTOR, gc);
                    Logger.log(Logger.INTERESTING,
                            "Power spike of " + String.format("%.1f", amount) + "!");
				} else {
					amount = -randomAmount(FLUX_FIXED_FACTOR, gc);
                    Logger.log(Logger.INTERESTING,
                            "Power drain of " + String.format("%.1f", amount) + "!");
				}
				gc.addToLevel(amount);
			}
		}
	}


    private double randomAmount(double factor, PowerSource ps) {
		return MyRandom.nextDouble() * ps.getStartingPower() * factor;
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

}
