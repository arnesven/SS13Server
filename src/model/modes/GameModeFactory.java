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
        } else if (selectedMode.toLowerCase().equals("rogue ai")) {
		    Logger.log("Mode is rogue ai");
		    return new RogueAIMode();
		} else if (selectedMode.toLowerCase().equals("armageddon")) {
            Logger.log(Logger.INTERESTING, "Mode is ARMAGEDDON");
            return new ArmageddonGameMode();
        } else if (selectedMode.toLowerCase().equals("mutiny")) {
            Logger.log(Logger.INTERESTING, "Mode is Mutiny");
            return new MutinyGameMode();
        } else if (selectedMode.toLowerCase().equals("creative")) {
            Logger.log(Logger.INTERESTING, "Mode is Creative");
            return new CreativeGameMode();
		} else if (selectedMode.toLowerCase().equals("wizard")) {
			Logger.log(Logger.INTERESTING, "Mode is Wizard");
			return new WizardGameMode();
		} else if (selectedMode.toLowerCase().equals("mixed")) {
		    Logger.log(Logger.INTERESTING, "Mode is Mixed");
		    return new MixedGameMode();
        } else if (selectedMode.toLowerCase().equals("escape")) {
		    Logger.log("Mode is Escape");
		    return new EscapeGameMode();
        }
		
		Logger.log(Logger.INTERESTING, "Mode is secret");
		return SecretGameMode.getNewInstance();

	}

}
