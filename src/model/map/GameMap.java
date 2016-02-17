package model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author erini02
 * Class for representing the Game's map, i.e. the space station and its rooms.
 * This is just a container class which has references to two underlying
 * data structures with the rooms, a map and a list.
 */
public class GameMap {

	private HashMap<Integer, Room> roomsMap;
	private List<Room> roomsList;

	public void setRoomsList(ArrayList<Room> result) {
		roomsList = result;
	}

	public void setRoomMap(HashMap<Integer, Room> hm) {
		roomsMap = hm;
	}

	public List<Room> getRooms() {
		return roomsList;
	}

	public Room getRoomForID(int ID) {
		return roomsList.get(ID-1);
	}

}
