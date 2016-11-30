package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import model.GameData;


public abstract class AbstractCommandHandler implements MessageHandler {
	
	protected GameData gameData;

	public AbstractCommandHandler(GameData gameData) {
		this.gameData = gameData;
	}


	public abstract boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException;

	@Override
	public boolean handle(String message, ObjectOutputStream oos)
			throws IOException {

		//	if (message.startsWith("CL")){
		Scanner sc = new Scanner(message);
		String clid = sc.next();

		if (gameData.getPlayerForClid(clid) == null && !message.contains("RETURNING")) {
			oos.writeObject("ERROR: Server has been restarted, please rejoin.");
			return true;
		}

		//	System.out.println(clid);
		if (sc.hasNext()) {
			String command = sc.next();

			String rest = "";
			if (sc.hasNext()) {
				rest = sc.nextLine();
			}

			return handleCommand(command, clid, rest, oos);

		} else if (message.equals(" RETURNING")) {
            oos.writeObject("ERROR: If you are returning to a game, you must enter a player name.");
			return true;
		} else {
            return false;
        }
		//		}

	//	return false;
	}

}
