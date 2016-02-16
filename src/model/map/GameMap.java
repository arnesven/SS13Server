package model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
