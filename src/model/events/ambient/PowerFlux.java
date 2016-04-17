package model.events.ambient;

import model.events.Event;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.objects.consoles.GeneratorConsole;

public class PowerFlux extends Event {

	private static double FLUX_SUSTAIN_FACTOR = 0.2; // maximum sustained power flux is 30% of normal power (per turn)
	private static double FLUX_FIXED_FACTOR = 0.4;   // maximum power spike/drain is 80% of normal power
	private boolean sustaining = false;
	private boolean increasing = false;

	public double getProbability() {
		return 0.08;
	}
	
	@Override
	public void apply(GameData gameData) {
		GeneratorConsole gc = GeneratorConsole.find(gameData);
		if (sustaining ) {
			
			gc.addToLevel(randomAmount(FLUX_SUSTAIN_FACTOR) * (increasing ?1.0:-1.0));
			if (MyRandom.nextDouble() < 0.33 || gc.getPowerLevel() == 0.0) {
				System.out.println("Sustained power flux over");
				sustaining = false;
			}
			
		} else if (MyRandom.nextDouble() < getProbability()) {
			if (MyRandom.nextDouble() < 0.5) {
				sustaining = true;
				increasing = MyRandom.nextDouble() < 0.2;
				System.out.println("Sustained " + (increasing?"positive":"negative") + 
								   "power flux began");
			} else {
				double amount;
				if (MyRandom.nextDouble() < 0.3) {
					amount = randomAmount((FLUX_FIXED_FACTOR));
					System.out.println("Power spike of " + String.format("%.1f", amount) + "!");
				} else {
					amount = -randomAmount((FLUX_FIXED_FACTOR));
					System.out.println("Power drain of " + String.format("%.1f", amount) + "!");				
				}
				gc.addToLevel(amount);
			}
		}
	}

	private double randomAmount(double factor) {
		return MyRandom.nextDouble() * GeneratorConsole.STARTING_POWER * factor;
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
