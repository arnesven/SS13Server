package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import util.MyStrings;


public class MapCommandHandler extends AbstractCommandHandler {


	public MapCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
//		System.out.println("handling map command");
		if (command.contains("MAP")) { // TODO: take care of clients width and height. could be useful!
			String result = MyStrings.join(gameData.getPlayerForClid(clid).getVisibleMap(gameData));
			//Logger.log(result);
			oos.writeObject(result);
			return true;
		}
		
		return false;
	}

}
