package model.map;

import model.Actor;
import model.GameData;
import model.events.Event;
import model.events.ambient.ColdEvent;
import model.items.NoSuchThingException;
import model.events.NoPressureEverEvent;
import model.map.rooms.Room;
import model.map.rooms.RoomType;
import model.map.rooms.SpaceRoom;
import util.Logger;
import util.MyRandom;

import java.io.Serializable;
import java.util.*;


/**
 * @author erini02
 * Class for representing the Game's map, i.e. the space station and its rooms.
 * This is just a container class which has references to two underlying
 * data structures with the rooms, a map and a list.
 */
public class GameMap implements Serializable {

    public static final String STATION_LEVEL_NAME = "ss13";
    private static String[] SS13AreaNames;
    //private List<Room> roomsList;
    private Map<String, Map<String, Set<Room>>> levels = new TreeMap<>();
    private String[][][] levelMatrix = new String[3][3][3];
    private int levelCount = 0;
    public static final int MATRIX_DIM_MAX = 3;
    private static final int MAX_LEVELS = MATRIX_DIM_MAX * MATRIX_DIM_MAX * MATRIX_DIM_MAX;

    private static Map<String, Integer[]> directions = new HashMap<>();



    public GameMap(String firstLevelName) {
        createLevel(firstLevelName);
        SS13AreaNames = new String[]{"aft", "port", "front", "starboard", "center"};

        directions.clear();
        directions.put("Upwards",   new Integer[]{ 0,  0,  1});
        directions.put("Downwards", new Integer[]{ 0,  0, -1});
        directions.put("Left",      new Integer[]{-1,  0,  0});
        directions.put("Right",     new Integer[]{ 1,  0,  0});
        directions.put("Forwards",  new Integer[]{ 0,  1,  0});
        directions.put("Backwards", new Integer[]{ 0, -1,  0});
    }

    public static String[] getSS13AreaNames() {
        return SS13AreaNames;
    }

    public void addRoom(Room room, String level, String area) {
        if (!levels.containsKey(level)) {
            createLevel(level);

        }

        if (levels.get(level).containsKey(area)) {
            levels.get(level).get(area).add(room);
        } else {
            Set<Room> set = new HashSet<>();
            set.add(room);
            levels.get(level).put(area, set);
        }
        room.setMap(this);
	}


    private void createLevel(String level, Integer x, Integer y, Integer z) {
        if (levelCount == MAX_LEVELS) {
            throw new MapOverflowException("Too many levels (max " + MAX_LEVELS + ")");
        }
        levels.put(level, new HashMap<>());
        levelCount++;

        levelMatrix[x][y][z] = level;

        Logger.log("Level " + level + " created on coordinates " + x + " " + y + " " + z + " ");


    }

    private void createLevel(String level) {
        int x, y, z;
        do {
            x = MyRandom.nextInt(3);
            y = MyRandom.nextInt(3);
            z = MyRandom.nextInt(3);
            if (levelMatrix[x][y][z] == null) {
                break;
            }
        } while (true);
        createLevel(level, x, y, z);

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
        return SS13AreaNames[side];

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
        boolean removed = false;
        for (Map<String, Set<Room>> level : levels.values()) {
            for (Set<Room> area : level.values()) {
                if (area.contains(bombRoom)) {
                    area.remove(bombRoom);
                    //return;
                     removed = true;
                }
            }
        }
        if (!removed) {
            throw new NoSuchThingException("No room " + bombRoom.getName() + "!");
        }
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
            if (position.getDoors()[i] == x && position.getDoors()[i+1] == y) {
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
        throw new NoSuchThingException("Room not found in any level: " + current);
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


    public static List<String> getDirectionStrings() {
        List<String> l = new ArrayList<>();
        l.addAll(directions.keySet());
        return l;
    }

    public static String getOppositeDirection(String direction) {

        List<String> directions = new ArrayList<>();
        directions.add("Upwards");
        directions.add("Downwards");
        directions.add("Left");
        directions.add("Right");
        directions.add("Forwards");
        directions.add("Backwards");

        for (int i = 0; i < directions.size() ; ++i) {
            if (directions.get(i).equals(direction)) {
                if (i % 2 == 0) {
                    if (i == 0) {
                        return directions.get(directions.size() - 1);
                    } else {
                        return directions.get(i - 1);
                    }
                } else {
                    if (i == directions.size() - 1) {
                        return directions.get(0);
                    } else {
                        return directions.get(i + 1);
                    }
                }
            }
        }
        throw new NoSuchElementException("Could not find opposite direction for direction " + direction);
    }

    public void tumbleIntoLevel(GameData gameData, Actor performingClient, String oppositeDirection) {
        Integer[] dir = directions.get(oppositeDirection);

        try {
            Integer[] current = getPositionForLevel(this.getLevelForRoom(performingClient.getPosition()));

            for (int dim = 0; dim < MATRIX_DIM_MAX; ++dim) {
                current[dim] += dir[dim];
                if (current[dim] == -1) {
                    current[dim] = MATRIX_DIM_MAX - 1;
                } else if (current[dim] == MATRIX_DIM_MAX) {
                    current[dim] = 0;
                }
            }

            String level = levelMatrix[current[0]][current[1]][current[2]];
            if (level == null) {
                level = createEmptyLevel(current, gameData);
            }
            Logger.log("Tumbling player to " + level);
            Set<Room> set = new HashSet<>();
            set.addAll(getRoomsForLevel(level));
            set.removeIf((Room r) -> r.getType() == RoomType.hidden);
            Room r = MyRandom.sample(set);
            performingClient.moveIntoRoom(r);


        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    private String createEmptyLevel(Integer[] current, GameData gameData) {
        String level = "emptylevel" + current[0] + "-" + current[1] + "-" + current[2];
        createLevel(level, current[0], current[1], current[2]);
        Room r = new SpaceRoom(getMaxID()+1, 0, 0, 2,2);
        this.addRoom(r, level, "space");
        Event noPress = new NoPressureEverEvent(r);
        r.addEvent(noPress);
        Event cold = new ColdEvent(r);
        r.addEvent(cold);
        gameData.addEvent(cold);
        gameData.addEvent(noPress);
        return level;
    }


    public Integer[] getPositionForLevel(String levelForRoom) {
        for (int x = 0; x < levelMatrix.length; ++x) {
            for (int y = 0; y < levelMatrix[0].length; y++) {
                for (int z = 0; z < levelMatrix[0][0].length; z++) {
                    if (levelMatrix[x][y][z] != null) {
                        if (levelMatrix[x][y][z].equals(levelForRoom)) {
                            return new Integer[]{x, y, z};
                        }
                    }
                }
            }
        }
        throw new NoSuchElementException("Level not found in map: " + levelForRoom);
    }

    public String getLevelForCoordinates(Integer[] selected, GameData gameData) {
        String level = levelMatrix[selected[0]][selected[1]][selected[2]];
        if (level == null) {
            level = createEmptyLevel(selected, gameData);
        }
        return level;
    }

    public Collection<Integer[]> getEmptyQuandrants() {
        Set<Integer[]> empties = new HashSet<>();
        for (int x = 0; x < levelMatrix.length; ++x) {
            for (int y = 0; y < levelMatrix[0].length; ++y) {
                for (int z = 0; z < levelMatrix[0][0].length; ++z) {
                    if (levelMatrix[x][y][z] == null || levelMatrix[x][y][z].contains("emptylevel")) {
                        empties.add(new Integer[]{x, y, z});
                    }
                }
            }
        }
        return empties;
    }

    public void swapLevels(String levelA, String levelB) {
        Integer[] posA = getPositionForLevel(levelA);
        Integer[] posB = getPositionForLevel(levelB);

        levelMatrix[posA[0]][posA[1]][posA[2]] = levelB;
        levelMatrix[posB[0]][posB[1]][posB[2]] = levelA;

    }

    public void setSS13AreaNames(String[] SS13AreaNames) {
        this.SS13AreaNames = SS13AreaNames;
    }

    public Collection<String> getLevels() {
        return levels.keySet();
    }

    public void moveRoomToLevel(Room r, String level, String area) {
        try {
            removeRoom(r);
            addRoom(r, level, area);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public static Room findClosest(Collection<Room> asteroids, Room miningStation) {
        Room closest = asteroids.iterator().next();
        int dist = (closest.getX() - miningStation.getX())*(closest.getX() - miningStation.getX()) +
                (closest.getY() - miningStation.getY())*(closest.getY() - miningStation.getY());
        for (Room r : asteroids) {
            int newdist = (r.getX() - miningStation.getX())*(r.getX() - miningStation.getX()) +
                    (r.getY() - miningStation.getY())*(r.getY() - miningStation.getY());
            if (newdist < dist) {
                dist = newdist;
                closest = r;
            }
        }
        return closest;
    }
}
