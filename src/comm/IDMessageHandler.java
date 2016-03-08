package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import model.GameState;


public class IDMessageHandler implements MessageHandler {
	
	private GameData gameData;

	public IDMessageHandler(GameData gameData) {
		this.gameData = gameData;
	}

	@Override
	public boolean handle(String message, ObjectOutputStream oos) throws IOException {
		if (message.contains("IDENT ME")) {
			if (gameData.getGameState() == GameState.PRE_GAME) {
				String clid = gameData.createNewClient(message.replace("IDENT ME", ""));
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
