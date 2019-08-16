package model.map.rooms;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import graphics.ClientInfo;
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
import model.npcs.NPC;
import model.objects.general.PowerConsumer;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;
import util.Logger;


/**
 * @author erini02
 * Class for representing a room on the space station.
 */
public class Room implements ItemHolder, PowerConsumer, Serializable {



    private final RoomType roomType;
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
	private final Sprite floorSprite;
	private static final Sprite DOOR_SPRITE = new Sprite("doorsprite", "doors.png", 8, 18, null);

	/**
	 * These fields are purely for the GUI.
	 */
	private String shortname;
	private int x;
	private int y;
	private int width;
	private int height;
	private int ID;
	private double[] doors;
    private String shortName;


    /**
	 * Constructor for a Room
	 * @param ID, the numeric ID of the room. This is used for the most part when identifying it
	 * @param name, the full name of the room.
	 * @param shortname, the short name for the room as it shows up on the client's map, for some rooms this short name is "".
	 * @param x, the rooms x-position (upper left corner).
	 * @param y, the rooms y-position (upper left corner).
	 * @param width the width of the room.
	 * @param height the height of the room
	 * @param neighbors what hidden rooms (their IDs) are connected to this room by doors.
	 * @param doors where doors should show up visually on the map (no game effect) 
	 */
	public Room(int ID, String name, String shortname, int x, int y, int width, int height, int[] neighbors, double[] doors, RoomType roomType) {
		this.name = name;
		this.shortname = shortname;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ID = ID;
		this.neighbors = neighbors;
		this.doors=doors;
        this.roomType = roomType;
        this.floorSprite = getfloorSpriteForColor();
	}

    @Override
	public String toString() {
		String result = ID + ":" + name + ":" + shortname + ":" + x + ":" + y + ":" + 
						width + ":" + height +":" + Arrays.toString(neighbors) + ":" + Arrays.toString(doors) + ":" + floorSprite.getName();
		//Logger.log(result);
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
            a.addYourselfToRoomInfo(info, whosAsking);
		}
		
		for (GameObject ob : objects) {
			ob.addYourselfToRoomInfo(info, whosAsking);	
		}
		for (GameItem it : items) {
			it.addYourselfToRoomInfo(info, whosAsking);
		}
		
		//Collections.sort(info);
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

	public String getShortname() {
		return shortname;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double[] getDoors() {
		return doors;
	}

    public void setDoors(double[] doors) {
        this.doors = doors;
    }


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

    public RoomType getType() {
        return roomType;
    }


    public String getColor() {
        Color backgroundcolor;
        switch (roomType) {
            case command:
                backgroundcolor = new Color(100, 190, 255);
                break;
            case science:
                backgroundcolor = new Color(70, 200, 150);
                break;
            case security:
                backgroundcolor = new Color(255, 130, 100);
                break;
            case support:
                backgroundcolor = new Color(200, 150, 200);
                break;
            case tech:
                backgroundcolor = new Color(255, 210, 0);
                break;
            case space:
                backgroundcolor = new Color(0, 0, 0);
                break;
            case basestar:
                backgroundcolor = new Color(100, 100, 140);
                break;
            case button:
                backgroundcolor = new Color(17, 17, 17);
                break;
            default:
                backgroundcolor = new Color(217, 217, 217);
        }
        return String.format("#%02X%02X%02X", backgroundcolor.getRed(), backgroundcolor.getGreen(), backgroundcolor.getBlue());
    }


    private Sprite getfloorSpriteForColor() {
        switch (roomType) {
            case command:
                return new Sprite("floor"+getColor(), "floors.png", 27, 1, null);
            case science:
                return new Sprite("floor"+getColor(), "floors.png", 27, 2, null);
            case security:
                return new Sprite("floor"+getColor(), "floors.png", 1, 1, null);
            case support:
                return new Sprite("floor"+getColor(), "floors.png", 17, 7, null);
            case tech:
                return new Sprite("floor"+getColor(), "floors.png", 1, 4, null);
            case space:
                return new Sprite("floor"+getColor(), "floors.png", 18, 24, null);
            case hall:
                return new Sprite("floorhall", "floors.png", 0, 0, null);
            case airlock:
                return new Sprite("floorairlock", "floors.png", 22, 28, null);

        }

        return  new Sprite("floor"+getColor(), "floors.png", 29, 28, null);
    }


    @Override
    public double getPowerConsumptionFactor() {
        return 1.0;
    }

    protected void setCoordinates(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortname(String shortName) {
        this.shortName = shortName;
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

    public boolean hasBackgroundSprite() {
	    return false;
    }

    public Sprite getBackgroundSprite(ClientInfo clientInfo) {
        return null;
    }

    public List<Sprite> getAlwaysSprites() {return new ArrayList<>();};
}
