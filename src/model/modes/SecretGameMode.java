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
	
	private GameMode innerMode;

	public static GameMode getNewInstance() {
		GameMode result;
		double d = MyRandom.nextDouble();
		if (d < 0.45) {
			result = new HostGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else if (d < 0.9) {
			result = new TraitorGameMode() {
				@Override
				protected void addProtagonistStartingMessage(Player c) {
					protMessage(c);
				}
			};
		} else {
			result = new InfiltrationGameMode() {
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
