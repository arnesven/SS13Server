package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import util.Logger;


public class NextActionCommandHandler extends AbstractCommandHandler {

	public NextActionCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("NEXTACTION")) {
			String actionStr = rest.substring(1, rest.length());
			Logger.log(clid + "'s next action is " + actionStr);
			gameData.getPlayerForClid(clid).parseActionFromString(actionStr, gameData);
			oos.writeObject("ACK");
			return true;
		}
		return false;
	}



}
