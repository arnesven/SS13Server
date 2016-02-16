package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import model.GameData;


public class KickCommandHandler extends AbstractCommandHandler{

	
	

	public KickCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {

		Scanner sc = new Scanner(rest);

		if (command.equals("KICK")) {
			String otherPlayer = sc.next();
			if (! otherPlayer.equals(clid)) {
				gameData.removeClient(otherPlayer);
				System.out.println(clid + " kicked " + otherPlayer + " from the game!");
				oos.writeObject("KICKED");
			} else {
				oos.writeObject("SELF-KICK ERROR"); // may not kick yourself
			}
			sc.close();
			return true;
		}
		sc.close();
		
		return false;
	}

}
