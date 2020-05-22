package model.modes;

import util.Logger;
import util.MapSavedToDisk;
import util.MyArrays;
import util.MyRandom;
import model.Player;

import java.io.File;
import java.util.*;

public class SecretGameMode extends MapSavedToDisk<String, Double> {
	
	private static final String filename = "random_modes";
	private static final double[] probabilities = {0.20,      0.35,         0.15,         0.12,       0.04,     0.03,     0.05,     0.03,    0.03};
    private static final String[] modeNames =   {"Host", "Traitor", "Operatives", "Changeling", "Rogue AI", "Mutiny",  "Wizard", "Mixed", "Armageddon"};

	public SecretGameMode() {
		super("secret_probabilities");
		if (!(new File("secret_probabilities").exists())) {
			for (int i = 0; i < modeNames.length; ++i) {
				getEntries().put(modeNames[i], probabilities[i]);
			}
			writeFile();
		} else {
			readFile();
		}
	}


	public static GameMode getNewInstance() {
		SecretGameMode sgm = new SecretGameMode();
		GameMode result = null;

		List<String> modeNames = new ArrayList<>();
		List<Double> cummulative = new ArrayList<>();
		int j = 0;
		for (Map.Entry<String, Double> me : sgm.getEntries().entrySet()) {
			modeNames.add(me.getKey());
			double sum = me.getValue();
			if (j > 0) {
				sum += cummulative.get(j-1);
			}
			cummulative.add(sum);
			j++;
		}
		double d = MyRandom.nextDouble();

		printProbabilites(modeNames, cummulative);

		for (int i = 0; i < modeNames.size(); ++i) {
			if (d < cummulative.get(i)) {
				MyRandom.write_to_file(filename, d + " " + modeNames.get(i));
				result = GameModeFactory.createSecret(modeNames.get(i));
				Logger.log("...... but secretly it's " + modeNames.get(i));
				break;
			}
		}

		return result;
	}

    private static void printProbabilites(List<String> names, List<Double> cummulative) {
        System.out.println("Secret game mode cummulative:");
        double cum = 0.0;
        for (int i = 0; i < cummulative.size(); ++i) {
            System.out.println(names.get(i) + " " + (cummulative.get(i) - cum)*100.0 + "%");
            //System.out.println(modeNames[i] + " " + cummulative[i]*100.0 + "%");
            cum = cummulative.get(i);
        }
    }

    public static void protMessage(Player c) {
		c.addTolastTurnInfo("Just another day on SS13. What will happen today?");
	}


	public static String[] getModeNames() {
		return modeNames;
	}

	public static double getProbabilityForMode(String s) {
		int i = 0;
		for (String mode : modeNames) {
			if (mode.equals(s)) {
				return probabilities[i];
			}
			i++;
		}
		return 0.0;
	}


	private static String getDescription() {
		return "Only the antagonists know the actual game mode. The crew must figure out the mode and how to cope with it.";
	}

	private static String getImageURL() {
		return "https://milestonesolar.com/wp-content/uploads/2017/05/question-marks.jpg";
	}

	public static String getDescriptionHTML() {
		return "<table><tr><td>" +
				"<img width='170' height='110' src='" + getImageURL() + "'</img>" +
				"</td><td>" +
				getDescription() +
				"</td></tr></table>";
	}

	@Override
	protected Double readValue(Scanner scanner) {
		return scanner.nextDouble();
	}

	@Override
	protected String readKey(Scanner scanner) {
		return scanner.next().replace("_", " ");
	}

	@Override
	protected String StringifyValue(Double value) {
		return value.toString();
	}

	@Override
	protected String StringifyKey(String key) {
		return key.replace(" ", "_");
	}
}
