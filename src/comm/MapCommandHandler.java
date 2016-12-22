package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.GameData;
import util.Logger;
import util.MyStrings;


public class MapCommandHandler extends AbstractCommandHandler {


	public MapCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {
//		System.out.println("handling map command");
		if (command.contains("MAP")) {
			String result = MyStrings.join(gameData.getPlayerForClid(clid).getVisibleMap(gameData));
			//Logger.log("MAPCOMMAND:" + rest);
            rest = rest.substring(1);

                Scanner scan = new Scanner(rest);
                int width = scan.nextInt();
                int height = scan.nextInt();
//                int width = Integer.parseInt(rest.substring(0, (int) Math.ceil(rest.length() / 2.0)));
//                int height = Integer.parseInt(rest.substring((int) Math.ceil(rest.length() / 2.0)+1, rest.length()));

               // Logger.log("Clients dimension is " + width + "x" + height);
            gameData.getPlayerForClid(clid).setClientDimension(width, height);

			oos.writeObject(result);
			return true;
		}
		
		return false;
	}

}
