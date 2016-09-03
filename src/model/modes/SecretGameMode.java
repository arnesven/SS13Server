package model.modes;

import util.Logger;
import util.MyRandom;
import model.Player;

public class SecretGameMode  {
	
	private static final String filename = "random_modes";
    private static final double[] probabilities = {0.30, 0.60,     0.75,         0.90,         1.00};
    private static final String[] modeNames = {"Host", "Traitor", "Operatives", "Changeling", "Armageddon"};


	public static GameMode getNewInstance() {
		GameMode result;
        printProbabilites();
		double d = MyRandom.nextDouble();
		if (d < probabilities[0]) {
			MyRandom.write_to_file(filename, d + " Host");
			result = new HostGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
			Logger.log("...... but secretly it's host");
		} else if (d < probabilities[1]) {
			Logger.log("...... but secretly it's traitor");
			MyRandom.write_to_file(filename, d + " Traitor");
			result = new TraitorGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}

			};
		} else if (d < probabilities[2]) {
			MyRandom.write_to_file(filename, d + " Operatives");
			Logger.log("...... but secretly it's operatives");
			result = new OperativesGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else if (d < probabilities[3]){
			Logger.log("...... but secretly it's changeling");
			MyRandom.write_to_file(filename, d + " Changeling");
			result = new ChangelingGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else {
            Logger.log("...... but secretly it's armageddon");
            MyRandom.write_to_file(filename, d + " Armageddon");
            result = new ArmageddonGameMode() {
                @Override
                protected void addProtagonistStartingMessage(Player c) {
                    protMessage(c);
                }
            };
        }
		return result;
	}

    private static void printProbabilites() {
        System.out.println("Secret game mode probabilities:");
        double cum = 0.0;
        for (int i = 0; i < probabilities.length; ++i) {
            System.out.println(modeNames[i] + " " + (probabilities[i] - cum)*100.0 + "%");
            cum = probabilities[i];
        }
    }

    private static void protMessage(Player c) {
		c.addTolastTurnInfo("Just another day on SS13. What will happen today?");
	}

	
}
