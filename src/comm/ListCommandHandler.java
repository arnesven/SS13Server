package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.GameData;


public class ListCommandHandler extends AbstractCommandHandler {

	public ListCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		
		// System.out.println("handling list command");
		if (command.equals("LIST")) {
			String mess = gameData.getPollData();
//			System.out.println(mess);
			oos.writeObject(mess);
			return true;
		}
		
		return false;
	}

	

}
