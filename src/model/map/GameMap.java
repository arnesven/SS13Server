package model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import model.GameData;

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

	public void addRoom(Room room) {
		roomsList.add(room);
		roomsMap.put(room.getID(), room);
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

	public Room getRoom(String string) {
		for (Room r : roomsList) {
			if (r.getName().equals(string)) {
				return r;
			}
		}
		throw new NoSuchElementException("No room for given string " + string);
	}

	/**
	 * Gets the location for a particular side.
	 * 0 => aft side
	 * 1 => port side
	 * 2 => front side
	 * 3 => starboard side
	 * @return A list of lists of rooms.
	 */
	public List<List<Room>> getSideLocations() {
		List<List<Room>> list = new ArrayList<>();
		List<Room> aftSide = new ArrayList<>();
		aftSide.add(this.getRoom("Greenhouse"));
		aftSide.add(this.getRoom("Panorama Walkway"));
		aftSide.add(this.getRoom("Aft Walkway"));
		aftSide.add(this.getRoom("Airtunnel"));
		aftSide.add(this.getRoom("Chapel"));
		aftSide.add(this.getRoom("Aft Hall"));
		aftSide.add(this.getRoom("Lab"));
		aftSide.add(this.getRoom("Air Lock #1"));	
		list.add(aftSide);
		
		List<Room> portSide = new ArrayList<>();
		portSide.add(this.getRoom("Lab"));
		portSide.add(this.getRoom("Sickbay"));
		portSide.add(this.getRoom("Air Lock #3"));
		portSide.add(this.getRoom("Port Hall Aft"));
		portSide.add(this.getRoom("Shuttle Gate"));
		portSide.add(this.getRoom("Port Hall Front"));
		portSide.add(this.getRoom("Security Station"));
		portSide.add(this.getRoom("Air Lock #2"));
		list.add(portSide);
		
		List<Room> frontSide = new ArrayList<>();
		frontSide.add(this.getRoom("Security Station"));
		frontSide.add(this.getRoom("Port Hall Front"));
		frontSide.add(this.getRoom("Air Lock #2"));
		frontSide.add(this.getRoom("Bridge"));
		frontSide.add(this.getRoom("Captain's Quarters"));
		frontSide.add(this.getRoom("Front Hall"));
		frontSide.add(this.getRoom("Office"));
		list.add(frontSide);
		
		List<Room> starboardSide = new ArrayList<>();
		starboardSide.add(this.getRoom("Office"));
		starboardSide.add(this.getRoom("Starboard Hall Front"));
		starboardSide.add(this.getRoom("Dorms"));
		starboardSide.add(this.getRoom("Bar"));
		starboardSide.add(this.getRoom("Kitchen"));
		starboardSide.add(this.getRoom("Starboard Hall Aft"));
		starboardSide.add(this.getRoom("Aft Walkway"));
		starboardSide.add(this.getRoom("Air Lock #1"));
		list.add(starboardSide);
		
		return list;
	}

	
	public static void connectRooms(Room to, Room from) {
		int[] newNArr = new int[from.getNeighbors().length+1];
		for (int i = 0; i < from.getNeighbors().length; ++i) {
			newNArr[i] = from.getNeighbors()[i];
		}
		newNArr[from.getNeighbors().length] = to.getID();
		from.setNeighbors(newNArr);
	}

	public static void disconnect(Room to, Room from) {
		int[] newNArr = new int[from.getNeighbors().length-1];
		int x = 0;
		for (int i = 0; i < from.getNeighbors().length; ++i) {
			if (from.getNeighbors()[i] != to.getID()) {
				newNArr[x] = from.getNeighbors()[i];
				x++;
			}
		}
	
		from.setNeighbors(newNArr);
	}




}
