package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import model.GameData;


public class ReadyCommandHandler extends AbstractCommandHandler {


	public ReadyCommandHandler(GameData gameData) {
		super(gameData);
	}


	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
		Scanner sc = new Scanner(rest);
		if (command.equals("READY")) {
			String yesNo = sc.next();
			gameData.setCientReady(clid, yesNo.equals("YES"));	
			oos.writeObject(gameData.makeStringFromReadyClients()+ ":" + gameData.getGameState().val);
			sc.close();
			return true;
		} 
		sc.close();
		return false;

	}

}
