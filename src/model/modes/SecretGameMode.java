package model.modes;

import util.MyRandom;
import model.Player;

public class SecretGameMode  {
	
	private static final String filename = "random_modes";

	public static GameMode getNewInstance() {
		GameMode result;
		double d = MyRandom.nextDouble();
		if (d < 0.35) {
			MyRandom.write_to_file(filename, "Host");
			result = new HostGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
			System.out.println("...... but secretly its host");
		} else if (d < 0.70) {
			System.out.println("...... but secretly its traitor");
			MyRandom.write_to_file(filename, "Traitor");
			result = new TraitorGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}

			};
		} else if (d < 0.85){
			MyRandom.write_to_file(filename, "Operatives");
			System.out.println("...... but secretly it's operatives");
			result = new OperativesGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else {
			System.out.println("...... but secretly its changeling");
			MyRandom.write_to_file(filename, "Changeling");
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
