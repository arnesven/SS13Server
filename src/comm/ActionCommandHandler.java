package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;


public class ActionCommandHandler extends AbstractCommandHandler {

	public ActionCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("ACTIONS")) {
			oos.writeObject(gameData.createPlayerActionData(clid));
			return true;
		}
		
		return false;
	}



}
