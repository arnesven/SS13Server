package model.map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.Action;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.events.ElectricalFire;
import model.events.Event;
import model.events.HullBreach;
import model.items.GameItem;
import model.npcs.NPC;
import model.objects.GameObject;


/**
 * @author erini02
 * Class for representing a room on the space station.
 */
public class Room {

	private String name;
	private String shortname;
	private int x;
	private int y;
	private int width;
	private int height;
	private int ID;
	private int[] neighbors;
	private double[] doors;
	
	private List<Player> players = new ArrayList<>();
	private List<NPC> npcs = new ArrayList<>();
	private List<GameObject> objects = new ArrayList<>();
	private List<Action> actionsHappened = new ArrayList<>();
	private GameMap map = null;
	private List<GameItem> items = new ArrayList<>();
	private List<Event> events = new ArrayList<>();
	private List<Event> eventsHappened = new ArrayList<>();

	/**
	 * Constructor for a Room
	 * @param ID, the numeric ID of the room. This is used for the most part when identifying it
	 * @param name, the full name of the room.
	 * @param shortname, the short name for the room as it shows up on the client's map, for some rooms this short name is "".
	 * @param x, the rooms x-position (upper left corner).
	 * @param y, the rooms y-position (upper left corner).
	 * @param width the width of the room.
	 * @param height the height of the room
	 * @param neighbors what other rooms (their IDs) are connected to this room by doors.
	 * @param doors where doors should show up visually on the map (no game effect) 
	 */
	public Room(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors) {
		this.name = name;
		this.shortname = shortname;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ID = ID;
		this.neighbors = neighbors;
		this.doors=doors;
	}
	
	@Override
	public String toString() {
		String result = ID + ":" + name + ":" + shortname + ":" + x + ":" + y + ":" + 
						width + ":" + height +":" + Arrays.toString(neighbors) + ":" + Arrays.toString(doors);
		return result;
	}

	/**
	 * Gets the ID of the room.
	 * @return the ID of the room.
	 */
	public Integer getID() {
		return this.ID;
	}

	/**
	 * Gets this rooms neighbors as an array of room IDs.
	 * @return the rooms neighbors.
	 */
	public int[] getNeighbors() {
		return this.neighbors;
	}
	
	public void setNeighbors(int[] newNArr) {
		this.neighbors = newNArr;
	}


	/**
	 * Gets the info about this room as it appears to anyone standing in it.
	 * @param whosAsking The player who is requesting this information. Rooms may reveal 
	 * different information depending who is asking.
	 * @return the information as a list of strings.
	 */
	public List<String> getInfo(Player whosAsking) {
		ArrayList<String> info = new ArrayList<>();
		ArrayList<Actor> actors = new ArrayList<>();
		actors.addAll(players);
		actors.addAll(npcs);
		Collections.shuffle(actors);
		for (Actor a : actors) {
			if (whosAsking != a) {
				a.addYourselfToRoomInfo(info, whosAsking);
			} else {
				info.add("aYou");
			}
		}
		
		for (GameObject ob : objects) {
			ob.addYourselfToRoomInfo(info, whosAsking);	
		}
		for (GameItem it : items) {
			it.addYourselfToRoomInfo(info, whosAsking);
		}
		
		Collections.sort(info);
		for (Event event : events) {
			info.add(0, event.addYourselfToRoomInfo(whosAsking));
		}
		
		return info;
	}

	/**
	 * Adds a player to this room.
	 * @param client, the player to be added to the room.
	 */
	public void addPlayer(Player client) {
		this.players.add(client);
	}

	/**
	 * Removes a player from this room.
	 * @param client to be removed from this room.
	 * @throws NoSuchElementException if the player is not in this room.
	 */
	public void removePlayer(Player client) {
		if (! this.players.contains(client)) {
			throw new NoSuchElementException("Tried to remove a player from a room who was not there!");
		}
		this.players.remove(client);
	}

	/**
	 * Gets the full name of this room.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds a game object to this room.
	 * @param gameObject the object to be added.
	 */
	public void addObject(GameObject gameObject) {
		objects.add(gameObject);
	}

	/**
	 * Gets a list of players who are currently in this room.
	 * @return the list of players (clients)
	 */
	public List<Player> getClients() {
		return players;
	}

	/**
	 * Gets the game objects in this room as a list.
	 * @return the list of game objects.
	 */
	public List<GameObject> getObjects() {
		return objects;
	}

	/**
	 * Adds an NPC to this room.
	 * @param npc, the NPC to be added to the room.
	 */
	public void addNPC(NPC npc) {
		this.npcs.add(npc);
	}

	/**
	 * Removes an NPC from this room.
	 * @param npc, the NPC to remove from this room.
	 */
	public void removeNPC(NPC npc) {
		if (!npcs.contains(npc)) {
			throw new NoSuchElementException("Tried removing NPC " + npc + " from the room, but it wasn't there!");
		}
		this.npcs.remove(npc);
	}

	public List<Room> getNeighborList() {
		List<Room> list = new ArrayList<>();
		for (int i = 0; i < neighbors.length; ++i) {
			list.add(map.getRoomForID(getNeighbors()[i]));
		}
		return list;
	}

	public void addActionsFor(GameData gameData, Player client, ArrayList<Action> at) {
		for (GameObject ob : objects) {
			ob.addSpecificActionsFor(gameData, client, at);
		}
	}

	public void setMap(GameMap gameMap) {
		this.map = gameMap;
	}

	/**
	 * Returns the npcs in this room.
	 * @return
	 */
	public List<NPC> getNPCs() {
		return npcs;
	}

	public void addItem(GameItem item) {
		this.items.add(item);
	}

	public List<GameItem> getItems() {
		return items;
	}

	public List<Actor> getActors() {
		List<Actor> list = new ArrayList<>();
		list.addAll(players);
		list.addAll(npcs);
		return list;
	}

	public void addToActionsHappened(Action action) {
		actionsHappened.add(action);
	}

	public void clearHappenings() {
		actionsHappened.clear();	
		eventsHappened.clear();
	}

	public List<Action> getActionsHappened() {
		return actionsHappened;
	}

	public void pushHappeningsToPlayers() {
		for (Action a : getActionsHappened()) {
			for (Player p : players) {
				if (p.getCharacter().doesPerceive(a)) {
					String text = a.getDescription(p);
					if (!text.contains(p.getPublicName()) && !text.toLowerCase().contains("you")) {
						p.addTolastTurnInfo(text);
					}
				}				
			}
			
		}
		
		for (Action a : getActionsHappened()) {
			if (a.getSense().sound == AudioLevel.VERY_LOUD || 
				a.getSense().smell == OlfactoryLevel.SHARP) {
				for (Room r : getNeighborList()) {
					for (Player p : r.getClients()) {
						if (p.getCharacter().doesPerceive(a)) {
							String text = a.getDistantDescription(p);
							p.addTolastTurnInfo(text);
						}				
					}
				}
			}
		}
		
		for (Event a : getEventsHappened()) {
			if (a.getSense().sound == AudioLevel.VERY_LOUD || 
				a.getSense().smell == OlfactoryLevel.SHARP) {
				for (Room r : getNeighborList()) {
					for (Player p : r.getClients()) {
						String text = a.getDistantDescription();
						p.addUniquelyTolastTurnInfo(text);
					}
				}
			}
		}
	}

	private List<Event> getEventsHappened() {
		return eventsHappened;
	}

	public void addEvent(Event e) {
		this.events.add(e);
	}

	public List<Target> getTargets() {
		List<Target> result = new ArrayList<>();
		result.addAll(players);
		for (GameObject o : objects) {
			if (o instanceof Target) {
				result.add((Target)o);
			}
		}
		return result;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void removeEvent(Event e) {
		events.remove(e);		
	}

	public boolean hasFire() {
		for (Event e : getEvents()) {
			if (e instanceof ElectricalFire) {
				return true;
			}
		}
		return false;
	}

	public boolean hasHullBreach() {
		for (Event e : getEvents()) {
			if (e instanceof HullBreach) {
				return true;
			}
		}
		return false;
	}

	public void addToEventsHappened(Event e) {
		this.eventsHappened.add(e);
		
	}



}
