package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.GameData;


public class MapCommandHandler extends AbstractCommandHandler {


	public MapCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		System.out.println("handling map command");
		if (command.equals("MAP")) {
			String result = gameData.getRooms().toString();
			System.out.println(result);
			oos.writeObject(result);
			return true;
		}
		
		return false;
	}

}
