package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;

public class SettingsCommandHandler extends AbstractCommandHandler {

	public SettingsCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		
		if (command.equals("SETTINGS")) {
			gameData.setSettings(rest);
			System.out.println("Setting settings " + rest);
			oos.writeObject("ACK");
			return true;
		}

		return false;
	}

}
