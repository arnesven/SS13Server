package model.objects;

import java.util.ArrayList;

import model.Player;
import model.actions.Action;

public class GameObject {
	private String name;
	
	public GameObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		info.add(this.name);
	}

	
	public void addSpecificActionsFor(Player cl, ArrayList<Action> at) {
	}

}
