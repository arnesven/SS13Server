package model.modes;

import util.Logger;
import util.MyRandom;
import model.Player;

public class SecretGameMode  {
	
	private static final String filename = "random_modes";

	public static GameMode getNewInstance() {
		GameMode result;
		double d = MyRandom.nextDouble();
		if (d < 0.35) {
			MyRandom.write_to_file(filename, d + " Host");
			result = new HostGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
			Logger.log("...... but secretly it's host");
		} else if (d < 0.70) {
			Logger.log("...... but secretly it's traitor");
			MyRandom.write_to_file(filename, d + " Traitor");
			result = new TraitorGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}

			};
		} else if (d < 0.85) {
			MyRandom.write_to_file(filename, d + " Operatives");
			Logger.log("...... but secretly it's operatives");
			result = new OperativesGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else {
			Logger.log("...... but secretly it's changeling");
			MyRandom.write_to_file(filename, d + " Changeling");
			result = new ChangelingGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		}
		return result;
	}

	private static void protMessage(Player c) {
		c.addTolastTurnInfo("Just another day on SS13. What will happen today?");
	}

	
}
