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
		} else if (selectedMode.toLowerCase().equals("operatives")) {
			System.out.println("Mode is operatives");
			return new OperativesGameMode();
		} else if (selectedMode.toLowerCase().equals("changeling")) {
			System.out.println("Mode is changeling");
			return new ChangelingGameMode();
		}
		
		System.out.println("Mode is secret");
		return SecretGameMode.getNewInstance();

	}

}
