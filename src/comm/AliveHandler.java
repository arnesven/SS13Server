package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import util.Logger;

public class AliveHandler implements MessageHandler {

	private GameData gameData;
	private String name;
	private int port;

	public AliveHandler(GameData gameData, String name, int port) {
		this.gameData = gameData;
		this.name = name;
		this.port = port;
	}

	@Override
	public boolean handle(String message, ObjectOutputStream oos)
			throws IOException {
		if (message.contains("ALIVE")) {
            Logger.log("Someone is pinging me...");
			oos.writeObject(this.name + ":" + gameData.getInfo());
			return true;
		}

		return false;
	}

}
