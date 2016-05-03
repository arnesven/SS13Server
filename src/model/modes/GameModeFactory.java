package model.modes;

import util.Logger;

public class GameModeFactory {
	
	public static GameMode create(String selectedMode) {
		if (selectedMode.toLowerCase().equals("host")) {
			Logger.log(Logger.INTERESTING, "Mode is host");
			return new HostGameMode();
		} else if (selectedMode.toLowerCase().equals("traitor")) {
			Logger.log(Logger.INTERESTING, "Mode is traitor");
			return new TraitorGameMode();			
		} else if (selectedMode.toLowerCase().equals("operatives")) {
			Logger.log(Logger.INTERESTING, "Mode is operatives");
			return new OperativesGameMode();
		} else if (selectedMode.toLowerCase().equals("changeling")) {
			Logger.log(Logger.INTERESTING, "Mode is changeling");
			return new ChangelingGameMode();
		} else if (selectedMode.toLowerCase().equals("armageddon")) {
            Logger.log(Logger.INTERESTING, "Mode is ARMAGEDDON");
            return new ArmageddonGameMode();
        }
		
		Logger.log(Logger.INTERESTING, "Mode is secret");
		return SecretGameMode.getNewInstance();

	}

}
