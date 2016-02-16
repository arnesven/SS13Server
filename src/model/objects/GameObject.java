package model.objects;

import java.util.ArrayList;

import model.Client;

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

}
