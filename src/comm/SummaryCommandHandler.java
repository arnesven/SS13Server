package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;

public class SummaryCommandHandler extends AbstractCommandHandler {

	public SummaryCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("SUMMARY")) {
			oos.writeObject(gameData.getSummary());
			return true;
		}
		return false;
	}

}
