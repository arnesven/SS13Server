package model.objects;

import java.util.ArrayList;

import model.Client;
import model.actions.Action;

public class GameObject {
	private String name;
	
	public GameObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

	public void addYourselfToRoomInfo(ArrayList<String> info, Client whosAsking) {
		info.add(this.name);
	}

	
	public void addSpecificActionsFor(Client cl, ArrayList<Action> at) {
	}

}
