package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import model.GameData;
import util.Logger;


public class NextMoveCommandHandler extends AbstractCommandHandler {

	public NextMoveCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("NEXTMOVE")) {
			Scanner sc = new Scanner(rest);
			Logger.log(clid + " selected location " + rest + " for move");
			int selected = sc.nextInt();
            gameData.getPlayerForClid(clid).setNextMove(selected);
			oos.writeObject("ACK");
			return true;
		}
		return false;
	}



}
