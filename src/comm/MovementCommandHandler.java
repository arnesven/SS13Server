package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;

import util.Logger;
import model.GameData;


public class MovementCommandHandler extends AbstractCommandHandler {

	public MovementCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("MOVEMENT")) {
			String s = gameData.createPlayerMovementData(clid);
			Logger.log(Logger.INTERESTING, s);
			oos.writeObject(s);
			return true;
		}
		return false;
	}



}
