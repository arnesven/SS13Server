package model.modes;

import util.MyRandom;

public class GameModeFactory {
	
	public static GameMode create(String selectedMode) {
		if (selectedMode.toLowerCase().equals("host")) {
			System.out.println("Mode is host");
			return new HostGameMode();
		} else if (selectedMode.toLowerCase().equals("traitor")) {
			System.out.println("Mode is traitor");
			return new TraitorGameMode();			
		} else if (selectedMode.toLowerCase().equals("infiltration")) {
			System.out.println("Mode is infiltration");
			return new InfiltrationGameMode();
		}
		
		System.out.println("Mode is secret");
		return SecretGameMode.getNewInstance();

	}

}
