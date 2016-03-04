package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;

public class JobsCommandHandler extends AbstractCommandHandler {

	public JobsCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("JOBS")) {
			//System.out.println("Got from client rest " + rest);
			if (rest.contains("{")) {
				gameData.getPlayerForClid(clid).parseJobChoices(rest);
			}
			String mess = gameData.getAvailableJobs();
		//	System.out.println(mess);
			oos.writeObject(mess);
			return true;
		}

		return false;
	}

}
