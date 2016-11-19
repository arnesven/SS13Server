package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import util.Logger;

public class SummaryCommandHandler extends AbstractCommandHandler {

    private String oldSummary = "No previous game summary...";

	public SummaryCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("SUMMARY")) {

            try {
                if (gameData.getGameMode() != null &&
                        gameData.getPlayerForClid(clid).getCharacter() != null &&
                        gameData.getGameMode().gameOver(gameData)) {
                    oldSummary = gameData.getSummary();
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
