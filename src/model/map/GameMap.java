package model.map;

import model.items.NoSuchThingException;

import java.io.Serializable;
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
public class GameMap implements Serializable {

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

//	public Room getRoomForID(int ID) {
//		return roomsList.get(ID-1);
//	}

	public Room getRoom(String string) throws NoSuchThingException {
		for (Room r : roomsList) {
			if (r.getName().equals(string)) {
				return r;
			}
		}
		throw new NoSuchThingException("No room for given string " + string);
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
        addIfAble(aftSide, "Greenhouse");
        addIfAble(aftSide, "Panorama Walkway");
        addIfAble(aftSide, "Aft Walkway");
        addIfAble(aftSide, "Airtunnel");
        addIfAble(aftSide, "Chapel");
        addIfAble(aftSide, "Aft Hall");
        addIfAble(aftSide, "Lab");
        addIfAble(aftSide, "Air Lock #1");
        list.add(aftSide);

        List<Room> portSide = new ArrayList<>();
        addIfAble(portSide, "Lab");
        addIfAble(portSide, "Sickbay");
        addIfAble(portSide, "Air Lock #3");
        addIfAble(portSide, "Port Hall Aft");
        addIfAble(portSide, "Shuttle Gate");
        addIfAble(portSide, "Port Hall Front");
        addIfAble(portSide, "Security Station");
        addIfAble(portSide, "Air Lock #2");
        list.add(portSide);

        List<Room> frontSide = new ArrayList<>();
        addIfAble(frontSide, "Security Station");
        addIfAble(frontSide, "Port Hall Front");
        addIfAble(frontSide, "Air Lock #2");
        addIfAble(frontSide, "Bridge");
        addIfAble(frontSide, "Captain's Quarters");
        addIfAble(frontSide, "Front Hall");
        addIfAble(frontSide, "Office");
        list.add(frontSide);

        List<Room> starboardSide = new ArrayList<>();
        addIfAble(starboardSide, "Office");
        addIfAble(starboardSide, "Starboard Hall Front");
        addIfAble(starboardSide, "Dorms");
        addIfAble(starboardSide, "Bar");
        addIfAble(starboardSide, "Kitchen");
        addIfAble(starboardSide, "Starboard Hall Aft");
        addIfAble(starboardSide, "Aft Walkway");
        addIfAble(starboardSide, "Air Lock #1");
        list.add(starboardSide);

        return list;
    }

    private void addIfAble(List<Room> side, String roomName) {
        try {
	  side.add(this.getRoom(roomName));
        } catch (NoSuchThingException nste) {
            // don't add it!
        }
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

//

    public int getMaxX() {
        int maxX = 0;
        for (Room r : roomsList) {
            if (r.getX() + r.getWidth() > maxX) {
                maxX = r.getX() + r.getWidth();
            }
        }
        return maxX;

    }

    public int getMinX() {
        int minX = 10000;
        for (Room r : roomsList) {
            if (r.getX() < minX) {
                minX = r.getX();
            }
        }
        return minX;
    }


    public int getMaxY() {
        int maxY = 0;
        for (Room r : roomsList) {
            if (r.getY() + r.getHeight() > maxY) {
                maxY = r.getY() + r.getHeight();
            }
        }
        return maxY;
    }

    public int getMinY() {
        int minY = 0;
        for (Room r : roomsList) {
            if (r.getY() < minY) {
                minY = r.getY();
            }
        }
        return minY;
    }

    public String getSideString(int side) {
        String sideStr = "central";

        if (side == 0) {
            sideStr = "aft";
        } else if (side == 1) {
            sideStr = "port";
        } else if (side == 2) {
            sideStr = "front";
        } else if (side == 3) {
            sideStr = "starboard";
        }
        return sideStr;
    }

    public List<Room> getStationRooms() {
        List<Room> station = new ArrayList<Room>();
        for (Room r : roomsList) {
            if (r.getType() != RoomType.hidden && r.getType() != RoomType.outer) {
                station.add(r);
            }
        }
        return station;
    }

    public void removeRoom(Room bombRoom) {
        roomsList.remove(bombRoom);
    }

    public Room getRoomByID(int i) throws NoSuchThingException {
        for (Room r : roomsList) {
            if (r.getID() == i) {
                return r;
            }
        }
        throw new NoSuchThingException("No room for ID " + i);
    }

    public int getMaxID() {
        int max = 0;
        for (Room r : roomsList) {
            if (r.getID() > max) {
                max = r.getID();
            }
        }
        return max;
    }

    public static void addDoor(Room position, double x, double y) {
        double[] newDoorArr = new double[position.getDoors().length + 2];
        int i = 0;
        for ( ; i < position.getDoors().length ; ++i) {
            newDoorArr[i] = position.getDoors()[i];
        }
        newDoorArr[i] = x;
        newDoorArr[i+1] = y;
        position.setDoors(newDoorArr);
    }
}
