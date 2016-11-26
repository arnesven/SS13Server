package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import model.modes.GameMode;
import util.Logger;

public class SummaryCommandHandler extends AbstractCommandHandler {

    private String oldSummary = "No previous game summary...";
    private GameMode mode = null;

	public SummaryCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("SUMMARY")) {

            try {
                if (mode != gameData.getGameMode()) {
                    oldSummary = gameData.getSummary(); // is still a bug here...
                    mode = gameData.getGameMode();
                }
            } catch (IllegalStateException ise) {
                Logger.log(Logger.INTERESTING, ise.getMessage());
            }
            oos.writeObject(oldSummary);
			return true;
		}
		return false;
	}

}
