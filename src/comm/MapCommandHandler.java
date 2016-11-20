package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import util.Logger;


public class MapCommandHandler extends AbstractCommandHandler {


	public MapCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
//		System.out.println("handling map command");
		if (command.equals("MAP")) {
			String result = gameData.getPlayerForClid(clid).getVsibleMap(gameData).toString();
			//Logger.log(result);
			oos.writeObject(result);
			return true;
		}
		
		return false;
	}

}
