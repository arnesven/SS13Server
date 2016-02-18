package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
	
		if (message.startsWith("CL")){
			Scanner sc = new Scanner(message);
			String clid = sc.next();
			
			if (gameData.getClient(clid) == null) {
				oos.writeObject("ERROR: OUT OF SYNC");
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
				
			} else {
				return false;
			}
		}

		return false;
	}

}
