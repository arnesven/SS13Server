package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import model.GameData;


public class NextMoveCommandHandler extends AbstractCommandHandler {

	public NextMoveCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		if (command.equals("NEXTMOVE")) {
			Scanner sc = new Scanner(rest);
			System.out.println(rest);
			gameData.getClient(clid).setNextMove(sc.nextInt());
			oos.writeObject("ACK");
			return true;
		}
		return false;
	}



}
