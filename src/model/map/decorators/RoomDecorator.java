package model.map.decorators;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.events.Event;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.map.GameMap;
import model.map.Room;
import model.npcs.NPC;
import model.objects.general.GameObject;

public class RoomDecorator extends Room {

	private Room innerRoom;
	
	public RoomDecorator(Room inner) {
		super(inner.getID(), inner.getName(), inner.getShortname(), inner.getX(), inner.getY(), 
				inner.getWidth(), inner.getHeight(), inner.getNeighbors(), inner.getDoors(), inner.getType());
		innerRoom = inner;
	}

	public Integer getID() {
		return innerRoom.getID();
	}

	public int[] getNeighbors() {
		return innerRoom.getNeighbors();
	}

	public void setNeighbors(int[] newNArr) {
		innerRoom.setNeighbors(newNArr);
	}

	public List<String> getInfo(Player whosAsking) {
		return innerRoom.getInfo(whosAsking);
	}

	public void addPlayer(Player client) {
		innerRoom.addPlayer(client);
	}

	public void removePlayer(Player client) throws NoSuchThingException {
		innerRoom.removePlayer(client);
	}

	public String getName() {
		return innerRoom.getName();
	}

	public void addObject(GameObject gameObject) {
		innerRoom.addObject(gameObject);
	}

	public List<Player> getClients() {
		return innerRoom.getClients();
	}

	public List<GameObject> getObjects() {
		return innerRoom.getObjects();
	}

	public void addNPC(NPC npc) {
		innerRoom.addNPC(npc);
	}

	public void removeNPC(NPC npc) throws NoSuchThingException {
		innerRoom.removeNPC(npc);
	}

	public List<Room> getNeighborList() {
		return innerRoom.getNeighborList();
	}

	public void addActionsFor(GameData gameData, Actor client,
                              ArrayList<Action> at) {
		innerRoom.addActionsFor(gameData, client, at);
	}

	public void setMap(GameMap gameMap) {
		innerRoom.setMap(gameMap);
	}

	public List<NPC> getNPCs() {
		return innerRoom.getNPCs();
	}

	public void addItem(GameItem item) {
		innerRoom.addItem(item);
	}

	public List<GameItem> getItems() {
		return innerRoom.getItems();
	}

	public List<Actor> getActors() {
		return innerRoom.getActors();
	}

	public void addToActionsHappened(Action action) {
		innerRoom.addToActionsHappened(action);
	}

	public void clearHappenings() {
		innerRoom.clearHappenings();
	}

	public List<Action> getActionsHappened() {
		return innerRoom.getActionsHappened();
	}

	public void pushHappeningsToPlayers() {
		innerRoom.pushHappeningsToPlayers();
	}

	public void addEvent(Event e) {
		innerRoom.addEvent(e);
	}

	public List<Target> getTargets() {
		return innerRoom.getTargets();
	}

	public List<Event> getEvents() {
		return innerRoom.getEvents();
	}

	public void removeEvent(Event e) {
		innerRoom.removeEvent(e);
	}

	public boolean hasFire() {
		return innerRoom.hasFire();
	}

	public boolean hasHullBreach() {
		return innerRoom.hasHullBreach();
	}

	public void addToEventsHappened(Event e) {
		innerRoom.addToEventsHappened(e);
	}
	
	
	
	
}
