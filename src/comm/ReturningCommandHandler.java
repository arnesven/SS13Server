package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import util.Logger;


public class ReturningCommandHandler extends AbstractCommandHandler {


	public ReturningCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("RETURNING")) {
			Logger.log("This client returning: " + clid);
			if (gameData.getClientsAsMap().containsKey(clid)) {
				oos.writeObject(clid);
			} else {
				oos.writeObject("ID ERROR");
			}
			return true;

		}
		return false;
	}

}
