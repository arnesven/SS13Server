package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

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
		if (command.contains("MAP")) {
	//	    Logger.log("Got MAP command");
		    //List<String> strs = makeStringsList(gameData.getPlayerForClid(clid).getVisibleMap(gameData));
			//String result = MyStrings.join(strs);
            String result;
            if (rest.contains("VISI")) {
                result = MyStrings.join(gameData.getPlayerForClid(clid).getVisibleMap(gameData));
            } else {
                result = MyStrings.join(gameData.getPlayerForClid(clid).getMiniMap(gameData));
            }
			//Logger.log("MAPCOMMAND:" + rest);
            rest = rest.substring(6);

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

//    private List<String> makeStringsList(List<Room> visibleMap) {
//	    List<String> strs = new ArrayList<>();
//        List<Integer> buttons = new ArrayList<>();
//
//
//	    for (Room r : visibleMap) {
//	        if (r.getType() == RoomType.button) {
//	            buttons.add(r.getID());
//	            Logger.log("    found a button " + r.getID());
//            }
//        }
//
//
//	    for (Room r : visibleMap) {
//            int[] newNeighs = new int[r.getNeighbors().length + buttons.size()];
//            for (int i = 0; i < r.getNeighbors().length; ++i) {
//                newNeighs[i] = r.getNeighbors()[i];
//            }
//            for (int i = 0; i < buttons.size(); ++i) {
//                newNeighs[r.getNeighbors().length+i] = buttons.get(i);
//            }
//            Logger.log(" SENDING ARRAY TO CLIENT " + Arrays.toString(newNeighs));
//
//            String result = r.getID() + ":" + r.getName() + ":" +
//                    r.getEffectName() + ":" + r.getX() + ":" + r.getY() + ":" +
//                    r.getWidth() + ":" + r.getHeight() + ":" + Arrays.toString(newNeighs) + ":" + Arrays.toString(r.getDoors()) + ":" + r.getColor();
//            strs.add(result);
//        }
//	    return strs;
//    }



}
