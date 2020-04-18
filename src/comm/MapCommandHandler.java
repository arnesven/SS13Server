package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import model.GameData;
import model.Player;
import model.map.rooms.Room;
import util.MyStrings;


public class MapCommandHandler extends AbstractCommandHandler {


	public MapCommandHandler(GameData gameData) {
		super(gameData);
	}

	@Override
	public boolean handleCommand(String command, String clid, String rest,
			ObjectOutputStream oos) throws IOException {

		if (command.contains("MAP")) {
			String result;
			Player player = gameData.getPlayerForClid(clid);
			if (rest.contains("VIMI")) {
				result = MyStrings.join(getVisibleMapStrings(player)) + "<vimi>" +
						MyStrings.join(getMiniMapStrings(player));
			} else if (rest.contains("VISI")) {
                result = MyStrings.join(getVisibleMapStrings(player));
            } else {
                result = MyStrings.join(getMiniMapStrings(player));
            }
            rest = rest.substring(6);

			Scanner scan = new Scanner(rest);
			int width = scan.nextInt();
			int height = scan.nextInt();

            gameData.getPlayerForClid(clid).setClientDimension(width, height);

			oos.writeObject(result);
			return true;
		}
		
		return false;
	}

	private List<String> getMiniMapStrings(Player player) {
		List<String> result = new ArrayList<>();
		for (Room r : player.getMiniMap(gameData)) {
			result.add(r.getStringRepresentation(gameData, player));
		}
		return result;
	}

	private List<String> getVisibleMapStrings(Player player) {
		List<String> result = new ArrayList<>();
		for (Room r : player.getVisibleMap(gameData)) {
			result.add(r.getStringRepresentation(gameData, player));
		}
		return result;
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
