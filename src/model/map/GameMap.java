package model.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * @author erini02
 * Class for representing the Game's map, i.e. the space station and its rooms.
 * This is just a container class which has references to two underlying
 * data structures with the rooms, a map and a list.
 */
public class GameMap {

	private List<Room> roomsList;

    private  GameMap() {
       // should not instanciate like this
    }

	public GameMap(ArrayList<Room> result) {
		for (Room r : result) {
			r.setMap(this);
		}
		this.setRoomsList(result);
	
	}

	public void addRoom(Room room) {
		roomsList.add(room);
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


	private void setRoomsList(ArrayList<Room> result) {
		roomsList = result;
	}

	public static boolean areJoined(Room a, Room b) {
        return a.getNeighborList().contains(b) &&
                b.getNeighborList().contains(a);
    }
	
	public static void joinRooms(Room a, Room b) {
		if (!areJoined(a, b)) {
			connectAToB(a, b);
			connectAToB(b, a);
		}
	}
	
	public static void separateRooms(Room a, Room b) {
		if (areJoined(a,b)) {
			disconnectAFromB(a, b);
			disconnectAFromB(b, a);
		}
	}
	
	private static void connectAToB(Room a, Room b) {
		int[] newNArr = new int[a.getNeighbors().length+1];
		for (int i = 0; i < a.getNeighbors().length; ++i) {
			newNArr[i] = a.getNeighbors()[i];
		}
		newNArr[a.getNeighbors().length] = b.getID();
		a.setNeighbors(newNArr);
	}

	private static void disconnectAFromB(Room a, Room b) {
		int[] newNArr = new int[a.getNeighbors().length-1];
		int x = 0;
		for (int i = 0; i < a.getNeighbors().length; ++i) {
			if (a.getNeighbors()[i] != b.getID()) {
				newNArr[x] = a.getNeighbors()[i];
				x++;
			}
		}
	
		a.setNeighbors(newNArr);
	}

	
	public static int shortestDistance(Room from, Room to) {
		return recursiveShortest(from, to, new HashSet<Room>());
	}

	private static int recursiveShortest(Room from, Room to,
			HashSet<Room> visited) {
		if (from == to) {
			return 0;
		}
		visited.add(from);
		
		int least = 100000;
		for (Room inter : from.getNeighborList()) {
			if (!visited.contains(inter)) {
				HashSet<Room> newVisited = new HashSet<>();
				newVisited.addAll(visited);
				least = Math.min(least, recursiveShortest(inter, to, newVisited));
			}
		}
		return 1 + least;
		
	}


    public int totalWidth() {
        int maxX = 0;
        for (Room r : roomsList) {
            if (r.getX() + r.getWidth() > maxX) {
                maxX = r.getX() + r.getWidth();
            }
        }
        return maxX;

    }

    public int getTotalHeight() {
        int maxY = 0;
        for (Room r : roomsList) {
            if (r.getY() + r.getHeight() > maxY) {
                maxY = r.getY() + r.getHeight();
            }
        }
        return maxY;
    }
}
