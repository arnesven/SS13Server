package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import model.GameData;


public class IDMessageHandler implements MessageHandler {
	
	private GameData gameData;

	public IDMessageHandler(GameData gameData) {
		this.gameData = gameData;
	}

	@Override
	public boolean handle(String message, ObjectOutputStream oos) throws IOException {
		if (message.equals("IDENT ME")) {
			if (gameData.getGameState() == 0) {
				String clid = gameData.createNewClient();


				System.out.println("This new dude gets " + clid);

				oos.writeObject(clid);
			} else {
				oos.writeObject("GAME ALREADY STARTED!");
			}
			return true;	

		} 

		return false;
	}

}
