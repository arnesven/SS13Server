package model.modes;

public class GameModeFactory {
	
	public static GameMode create(String selectedMode) {
		if (selectedMode.toLowerCase().equals("host")) {
			return new HostGameMode();
		} else if (selectedMode.toLowerCase().equals("traitor")) {
			return new TraitorGameMode();			
		}
		return new SecretGameMode();

	}

}
