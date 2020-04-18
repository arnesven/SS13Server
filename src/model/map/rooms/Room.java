package model.map.rooms;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import graphics.sprites.Sprite;
import model.*;
import model.actions.general.Action;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.events.NoPressureEverEvent;
import model.events.ambient.ColdEvent;
import model.events.ambient.DarkEvent;
import model.events.ambient.ElectricalFire;
import model.events.Event;
import model.events.ambient.HullBreach;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.RoomPartsStack;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.NormalDoor;
import model.map.floors.FloorSet;
import model.npcs.NPC;
import model.objects.general.PowerConsumer;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;
import util.Logger;


/**
 * @author erini02
 * Class for representing a room on the space station.
 */
public abstract class Room implements ItemHolder, PowerConsumer, Serializable {


    private String name;
	private int[] neighbors;
	private List<Player> players = new ArrayList<>();
	private List<NPC> npcs = new ArrayList<>();
	private List<GameObject> objects = new ArrayList<>();
	private List<Action> actionsHappened = new ArrayList<>();
	private GameMap map = null;
	private List<GameItem> items = new ArrayList<>();
	private List<Event> events = new ArrayList<>();
	private List<Event> eventsHappened = new ArrayList<>();
	private FloorSet floorSprite;

	/**
	 * These fields are purely for the GUI.
	 */
	private int x;
	private int y;
	private int z;
	private int width;
	private int height;
	private int ID;
	private Door[] doors;
    private List<Sprite> effect = new ArrayList<>();
	private static final RoomWalls walls = new RoomWalls();


	public Room(int ID, String name, int x, int y, int width, int height, int[] neighbors, double[] doors) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = 0;
		this.width = width;
		this.height = height;
		this.ID = ID;
		this.neighbors = neighbors;
		this.doors = NormalDoor.makeArrFromDoubleArr(doors);
        this.floorSprite = getFloorSet();
	}

	public abstract FloorSet getFloorSet();

	public void doSetup(GameData gameData) { }

	@Override
	public String toString() {
		String effectStr;
		if (effect.size() == 0) {
			effectStr = "";
		} else {
			effectStr = effect.get(0).getName();
		}

		floorSprite.registerSprites();
		String result = ID + ":" + name + ":" + effectStr + ":" + x + ":" + y + ":" + z + ":" +
						width + ":" + height +":" + Arrays.toString(neighbors) + ":" + Arrays.toString(doors) + ":" +
				floorSprite.getMainSprite().getName() + ":" + getAppearanceScheme();
		//Logger.log(result);
        return result;
	}

	protected String getAppearanceScheme() {
		String defaultApperance = "WallsAndWindows";
		if (map == null) {
			return defaultApperance + "-Space";
		}
		try {
			return defaultApperance + "-" + map.getLevelForRoom(this).getBackgroundType();
		} catch (NoSuchThingException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Should not happen!");
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
	public List<String> getInfo(GameData gameData, Player whosAsking) {
		ArrayList<String> info = new ArrayList<>();
		ArrayList<Actor> actors = new ArrayList<>();
		actors.addAll(players);
		actors.addAll(npcs);
		Collections.shuffle(actors);
		for (Actor a : actors) {
			if (a == whosAsking) {
				a.addYourselfToRoomInfo(info, whosAsking);
			}
		}

		for (Event event : events) {
			if (event.showSpriteInTopPanel()) {
				info.add(0, event.addYourselfToRoomInfo(whosAsking));
			}
		}

		ArrayList<Action> actionList = new ArrayList<>();
		whosAsking.getCharacter().addCharacterSpecificActions(gameData, actionList);
		for (Action a : actionList) {
			info.add(a.getAbilitySprite().getName() + "<img>" + a.getName() +  "<img>" +
					Action.makeActionListStringSpecOptions(gameData, List.of(a), whosAsking));
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
	public void removePlayer(Player client) throws NoSuchThingException {
		if (! this.players.contains(client)) {
			throw new NoSuchThingException("Tried to remove a player from a room who was not there!");
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

    public void removeObject(GameObject target) {
        objects.remove(target);
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

    public void addActor(Actor person) {
        if (person instanceof Player) {
            addPlayer((Player) person);
        } else if (person instanceof NPC) {
            addNPC((NPC) person);
        }
    }

	/**
	 * Removes an NPC from this room.
	 * @param npc, the NPC to remove from this room.
	 */
	public void removeNPC(NPC npc) throws NoSuchThingException {
		if (!npcs.contains(npc)) {
			throw new NoSuchThingException("Tried removing NPC " + npc + " from the room, but it wasn't there!");
		}
		this.npcs.remove(npc);
	}

    public void removeActor(Actor a) throws NoSuchThingException {
        if (npcs.contains(a)) {
            npcs.remove(a);
        } else if (players.contains(a)) {
            players.remove(a);
        } else {
            throw new NoSuchThingException("Tried removing Actor " + a.getBaseName() + " from the room, but it wasn't there!");
        }
    }

	public List<Room> getNeighborList() {
		List<Room> list = new ArrayList<>();
		for (int i = 0; i < neighbors.length; ++i) {
            try {
                list.add(map.getRoomByID(getNeighbors()[i]));
            } catch (NoSuchThingException e) {
                // Room does not exist any more
                Logger.log(Logger.CRITICAL, "Could not find room " + getNeighbors()[i]);
            }
        }
		return list;
	}

	public void addActionsFor(GameData gameData, Actor client, ArrayList<Action> at) {
		for (GameObject ob : objects) {
			if (client.getCharacter().canUseObjects()) {
				ob.addSpecificActionsFor(gameData, client, at);
			}
		}

        for (Actor a : getActors()) {
            a.getCharacter().addActionsForActorsInRoom(gameData, client, at);
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
        if (item.getHolder() == null) {
            item.gotAddedToRoom(null, this);
        } else {
            item.gotAddedToRoom(item.getHolder().getActor(), this);
        }
		item.setHolder(null);
		item.setPosition(this);
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
                    if (a.getPerformer() != null && a.getPerformer().getCharacter().isVisible()) {
                        a.experienceFor(p);
                    }
				}
            }
		}
		
		for (Action a : getActionsHappened()) {
			if (a.getSense().sound == AudioLevel.VERY_LOUD || 
				a.getSense().smell == OlfactoryLevel.SHARP) {
				for (Room r : getNeighborList()) {
					for (Player p : r.getClients()) {
						a.experienceFromAfarFor(p);
					}
				}
			}
		}
		
		for (Event a : getEventsHappened()) {
			if (a.getSense().sound == AudioLevel.VERY_LOUD || 
				a.getSense().smell == OlfactoryLevel.SHARP) {
				for (Room r : getNeighborList()) {
					for (Player p : r.getClients()) {
						a.experienceFromAfarFor(p);
					}
				}

			}
            for (Player p : players) {
                a.experienceFor(p);
            }
		}
	}

	private List<Event> getEventsHappened() {
		return eventsHappened;
	}

	public void addEvent(Event e) {
		this.events.add(e);
		e.gotAddedToRoom(this);
	}

	public List<Target> getTargets() {
		List<Target> result = new ArrayList<>();
		result.addAll(players);
        result.addAll(npcs);
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
		e.gotRemovedFromRoom(this);
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

	public List<Sprite> getEffects() {
		return effect;
	}

	public void addEffect(Sprite effect) {
		this.effect.add(effect);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() { return z; }

	protected void setZ(int i) {
		this.z = i;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double[] getDoorsAsDoubleArr() {
		return Door.makeDoubleArr(doors);
	}

    //public void setDoors(double[] doors) {
    //    this.doors = NormalDoor.makeArrFromDoubleArr(doors);
    //}


    public void removeFromRoom(GameItem searched) {
        if (items.contains(searched)) {
            items.remove(searched);
            Logger.log(Logger.INTERESTING, "Found object directly in room");
            return;
        } else {
            for (GameObject ob : getObjects()) {
                if (ob instanceof ContainerObject) {
                    if (((ContainerObject)ob).getInventory().contains(searched) ) {
                        ((ContainerObject)ob).getInventory().remove(searched);
                        Logger.log(Logger.INTERESTING, "Found object in container in room");
                        return;
                    }

                    for (GameItem it : ((ContainerObject) ob).getInventory()) {
                        if (it.getTrueItem() == searched) {
                            ((ContainerObject)ob).getInventory().remove(it);
                            Logger.log(Logger.INTERESTING, "Found object in hidden object in container in room");
                            return;
                        }
                    }
                }
            }
        }
        Logger.log(Logger.INTERESTING, "Did not find object " + searched.getBaseName() + " anywhere n room");

    }

    @Override
    public double getPowerConsumptionFactor() {
        return 1.0;
    }

    protected void setCoordinates(int newX, int newY, int z) {
        x = newX;
        y = newY;
        this.z = z;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void destroy(GameData gameData) {
        Iterator<Event> roomIter = this.getEvents().iterator();
        for (Event ev = null ; roomIter.hasNext(); ev = roomIter.next()) {
            if (ev instanceof ElectricalFire || ev instanceof HullBreach) {
                roomIter.remove();
            }
        }

        this.addEvent(new NoPressureEverEvent(this));
        this.addEvent(new ColdEvent(this));
        this.addEvent(new DarkEvent());

        for (Room neigh : this.getNeighborList()) {
            HullBreach hull = ((HullBreach) gameData.getGameMode().getEvents().get("hull breaches"));
            hull.startNewEvent(neigh);
            neigh.addItem(new RoomPartsStack(1));
        }

        try {
            gameData.getMap().removeRoom(this);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }


    public List<Sprite> getAlwaysSprites() {return new ArrayList<>();};

	public boolean isHidden() {
		return false;
	}

	public boolean isPartOfStation() { return true; }

	public void setFloorSet(FloorSet fs) {
		this.floorSprite = fs;
	}

	public void setDoors(Door[] doors) {
		this.doors = doors;
	}

	public Door[] getDoors() {
		return doors;
	}

	public void addDoor(Door newDoor) {
		Door[] newDoorArr = new Door[getDoors().length + 1];
		int i = 0;
		for (; i < getDoors().length ; ++i) {
			newDoorArr[i] = getDoors()[i];
		}
		newDoorArr[i] = newDoor;
		setDoors(newDoorArr);
	}

	public void removeDoorAt(double x, double y) {
		Door[] newDoorArr = new Door[getDoors().length - 1];
		int index = 0;
		for (int i = 0; i < getDoors().length ; i+=1) {
			if (!(getDoors()[i].getX() == x && getDoors()[i].getY() == y)) {
				newDoorArr[index] = getDoors()[i];
				index += 1;
			}
		}
	}

	public void removeDoor(Door oldDoor) {
		Door[] newDoorArr = new Door[getDoors().length - 1];
		int index = 0;
		for (int i = 0; i < getDoors().length; ++i) {
			if (getDoors()[i] != oldDoor) {
				newDoorArr[index++] = getDoors()[i];
			}
		}
		setDoors(newDoorArr);
	}


}
