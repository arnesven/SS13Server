package model.map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Client;
import model.objects.GameObject;


public class Room implements Comparable {

	private String name;
	private String shortname;
	private int x;
	private int y;
	private int width;
	private int height;
	private int ID;
	private int[] neighbors;
	private double[] doors;
	
	private List<Client> players = new ArrayList<>();
	private List<GameObject> objects = new ArrayList<>();

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

	public Integer getID() {
		return this.ID;
	}

	public int[] getNeighbors() {
		return this.neighbors;
	}

	public List<String> getInfo(Client whosAsking) {
		ArrayList<String> info = new ArrayList<>();
		for (Client cl : players) {
			cl.addYourselfToRoomInfo(info);
			
		}
		for (GameObject ob : objects) {
			ob.addYourselfToRoomInfo(info, whosAsking);	
		}
		
		return info;
	}

	public void addPlayer(Client client) {
		this.players.add(client);
	}

	public void removePlayer(Client client) {
		this.players.remove(client);
	}

	@Override
	public int compareTo(Object arg0) {
		return this.ID - ((Room)arg0).ID;
	}

	public String getName() {
		return name;
	}

	public void addObject(GameObject gameObject) {
		objects.add(gameObject);
	}

	public List<Client> getClients() {
		return players;
	}

}
