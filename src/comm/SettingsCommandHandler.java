package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.GameData;
import util.MyStrings;

public class SettingsCommandHandler extends AbstractCommandHandler {

	public SettingsCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		
		if (command.equals("SETTINGS")) {
			gameData.setSettings(rest, gameData.getPlayerForClid(clid));
			System.out.println("Setting settings " + rest);
			oos.writeObject(makeSettingsString(gameData.getPlayerForClid(clid).getSettings()));
			return true;
		}

		return false;
	}

    private String makeSettingsString(HashMap<String, Boolean> settings) {
        List<String> strs = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : settings.entrySet()) {
            strs.add(entry.getKey() + "," + entry.getValue());
        }
        return MyStrings.join(strs, "|");
    }

}
