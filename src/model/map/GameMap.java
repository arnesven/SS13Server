package model.map;

import model.items.NoSuchThingException;

import java.io.Serializable;
import java.util.*;


/**
 * @author erini02
 * Class for representing the Game's map, i.e. the space station and its rooms.
 * This is just a container class which has references to two underlying
 * data structures with the rooms, a map and a list.
 */
public class GameMap implements Serializable {

	//private List<Room> roomsList;
    private Map<String, Map<String, Set<Room>>> levels = new HashMap<>();


    public GameMap(String firstLevelName) {
        levels.put(firstLevelName, new HashMap<>());
    }

	public void addRoom(Room room, String level, String area) {
        if (!levels.containsKey(level)) {
            levels.put(level, new HashMap<>());
        }

        if (levels.get(level).containsKey(area)) {
            levels.get(level).get(area).add(room);
        } else {
            Set<Room> set = new HashSet<>();
            set.add(room);
            levels.get(level).put(area, set);
        }
	}
	
	public List<Room> getRooms() {
        Set<Room> list = new HashSet<>();
        for (Map<String, Set<Room>> m : levels.values()) {
            for (Set<Room> set : m.values()) {
                list.addAll(set);
            }
        }
        List<Room> result = new ArrayList<>();
        result.addAll(list);
        return result;
	}

//	public Room getRoomForID(int ID) {
//		return roomsList.get(ID-1);
//	}

	public Room getRoom(String string) throws NoSuchThingException {
		for (Room r : getRooms()) {
			if (r.getName().equals(string)) {
				return r;
			}
		}
		throw new NoSuchThingException("No room for given string " + string);
	}



//    private void addIfAble(List<Room> side, String roomName) {
//        try {
//	  side.add(this.getRoom(roomName));
//        } catch (NoSuchThingException nste) {
//            // don't add it!
//        }
//    }


//    private void setRoomsList(ArrayList<Room> result) {
//		roomsList = result;
//	}

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
        for (Room r : getRooms()) {
            if (r.getX() + r.getWidth() > maxX) {
                maxX = r.getX() + r.getWidth();
            }
        }
        return maxX;

    }

    public int getMinX() {
        int minX = 10000;
        for (Room r : getRooms()) {
            if (r.getX() < minX) {
                minX = r.getX();
            }
        }
        return minX;
    }


    public int getMaxY() {
        int maxY = 0;
        for (Room r : getRooms()) {
            if (r.getY() + r.getHeight() > maxY) {
                maxY = r.getY() + r.getHeight();
            }
        }
        return maxY;
    }

    public int getMinY() {
        int minY = 0;
        for (Room r : getRooms()) {
            if (r.getY() < minY) {
                minY = r.getY();
            }
        }
        return minY;
    }

    public static String getSideString(int side) {
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
//        for (Room r : roomsList) {
//            if (r.getType() != RoomType.hidden && r.getType() != RoomType.outer && r.getType() != RoomType.derelict) {
//                station.add(r);
//            }
//        }
        for (Set<Room> rooms : levels.get("ss13").values()) {
            station.addAll(rooms);
        }
        return station;
    }

    public void removeRoom(Room bombRoom) throws NoSuchThingException {
        for (Map<String, Set<Room>> level : levels.values()) {
            for (Set<Room> area : level.values()) {
                if (area.contains(bombRoom)) {
                    area.remove(bombRoom);
                    return;
                }
            }
        }
        throw new NoSuchThingException("No room " + bombRoom.getName() + "!");
    }

    public Room getRoomByID(int i) throws NoSuchThingException {
        for (Room r : getRooms()) {
            if (r.getID() == i) {
                return r;
            }
        }
        throw new NoSuchThingException("No room for ID " + i);
    }

    public int getMaxID() {
        int max = 0;
        for (Room r : getRooms()) {
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

    public static void removeDoor(Room position, double x, double y) {
        double[] newDoorArr = new double[position.getDoors().length - 2];
        int i = 0;
        int index = 0;
        for ( ; i < newDoorArr.length ; i+=2) {
            if (position.getDoors()[i] != x || position.getDoors()[i+1] != y) {
                newDoorArr[index]   = position.getDoors()[i];
                newDoorArr[index+1] = position.getDoors()[i+1];
                index += 2;
            }
        }
        position.setDoors(newDoorArr);
    }

    public static boolean hasDoor(Room position, double x, double y) {
        for (int i = 0; i < position.getDoors().length ; i+=2) {
            if (position.getDoors()[i] == x || position.getDoors()[i+1] == y) {
                return true;
            }
        }
        return false;
    }

    public Collection<Room> getArea(String ss13, String sideString) {
        return levels.get(ss13).get(sideString);
    }

    public String getAreaForRoom(String ss13, Room targetRoom) throws NoSuchThingException {
        for (Map.Entry<String, Set<Room>> entry : levels.get(ss13).entrySet()) {
            if (entry.getValue().contains(targetRoom)) {
                return entry.getKey();
            }
        }
        throw new NoSuchThingException("Room not found in any area in level " + ss13);
    }

    public String getLevelForRoom(Room current) throws NoSuchThingException {
        for (Map.Entry<String, Map<String, Set<Room>>> entry : levels.entrySet()) {
            for (Set<Room> entry2 : entry.getValue().values()) {
                for (Room r : entry2) {
                    if (r == current) {
                        return entry.getKey();
                    }
                }
            }
        }
        throw new NoSuchThingException("Room not found in any level");
    }

    public Collection<Room> getRoomsForLevel(String level) {
        Set<Room> result = new HashSet<>();
        for (Set<Room> set : levels.get(level).values()) {
            result.addAll(set);
        }
        return result;
    }

    public void setMapReferenceForAllRooms() {
        for (Room r : getRooms()) {
            r.setMap(this);
        }
    }
}
