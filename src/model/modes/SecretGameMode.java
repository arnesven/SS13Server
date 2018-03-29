package model.modes;

import util.Logger;
import util.MyArrays;
import util.MyRandom;
import model.Player;

import java.util.Arrays;

public class SecretGameMode  {
	
	private static final String filename = "random_modes";
	private static final double[] probabilities = {0.25,      0.25,         0.20,         0.15,       0.04,     0.04,     0.04,    0.03};
    private static final String[] modeNames =   {"Host", "Traitor", "Operatives", "Changeling", "Rogue AI", "Mutiny",  "Mixed", "Armageddon"};


	public static GameMode getNewInstance() {
		GameMode result;

		double d = MyRandom.nextDouble();

		double[] cummulative = MyArrays.prefixsum(probabilities);
        printProbabilites(cummulative);

		if (d < cummulative[0]) {
			MyRandom.write_to_file(filename, d + " Host");
			result = new HostGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
			Logger.log("...... but secretly it's host");
		} else if (d < cummulative[1]) {
			Logger.log("...... but secretly it's traitor");
			MyRandom.write_to_file(filename, d + " Traitor");
			result = new TraitorGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}

			};
		} else if (d < cummulative[2]) {
			MyRandom.write_to_file(filename, d + " Operatives");
			Logger.log("...... but secretly it's operatives");
			result = new OperativesGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else if (d < cummulative[3]){
			Logger.log("...... but secretly it's changeling");
			MyRandom.write_to_file(filename, d + " Changeling");
			result = new ChangelingGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else if (d < cummulative[4]) {
            Logger.log("...... but secretly it's rogue ai");
			MyRandom.write_to_file(filename, d + " Rogue AI");
			result = new RogueAIMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
        } else if (d < cummulative[5]) {
            Logger.log("...... but secretly it's mutiny");
            MyRandom.write_to_file(filename, d + " Mutiny");
            result = new MutinyGameMode() {
                @Override
                protected void addProtagonistStartingMessage(Player c) {
                    protMessage(c);
                }
            };
        } else if (d < cummulative[6]) {
            Logger.log("...... but secretly it's mixed");
            MyRandom.write_to_file(filename, d + " Mixed");
            result = new MixedGameMode() {
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

    private static void printProbabilites(double[] cummulative) {
        System.out.println("Secret game mode cummulative:");
        double cum = 0.0;
        for (int i = 0; i < cummulative.length; ++i) {
            System.out.println(modeNames[i] + " " + (cummulative[i] - cum)*100.0 + "%");
            //System.out.println(modeNames[i] + " " + cummulative[i]*100.0 + "%");
            cum = cummulative[i];
        }
    }

    public static void protMessage(Player c) {
		c.addTolastTurnInfo("Just another day on SS13. What will happen today?");
	}

	
}
