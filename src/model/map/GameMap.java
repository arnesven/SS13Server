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

	public GameMap(ArrayList<Room> result) {
		HashMap<Integer, Room> hm = new HashMap<>();
		for (Room r : result) {
			hm.put(r.getID(), r);
			r.setMap(this);
		}
		
		this.setRoomsList(result);
		this.setRoomMap(hm);
	
	}

	private void setRoomsList(ArrayList<Room> result) {
		roomsList = result;
	}

	private void setRoomMap(HashMap<Integer, Room> hm) {
		roomsMap = hm;
	}

	public List<Room> getRooms() {
		return roomsList;
	}

	public Room getRoomForID(int ID) {
		return roomsList.get(ID-1);
	}



}
