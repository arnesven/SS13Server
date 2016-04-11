package model.modes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.MyRandom;
import model.GameData;
import model.Player;
import model.characters.GameCharacter;
import model.events.Event;
import model.map.Room;
import model.npcs.NPC;

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
		} else if (d < 0.70) {
			MyRandom.write_to_file(filename, "Traitor");
			result = new TraitorGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else if (d < 0.85){
			MyRandom.write_to_file(filename, "Operatives");
			result = new OperativesGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else {
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
