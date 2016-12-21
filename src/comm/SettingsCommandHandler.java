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
            if (!rest.contains("undefined")) { // stupid fix, client sends a dummy message at start, ignore it
                gameData.setSettings(rest, gameData.getPlayerForClid(clid));
            }
			//System.out.println("Setting settings " + rest);
            String result = gameData.getPlayerForClid(clid).getSettings().makeIntoString();
            //System.out.print(result);
			oos.writeObject(result);
			return true;
		}

		return false;
	}



}
