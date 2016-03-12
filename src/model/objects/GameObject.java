package model.objects;

import java.util.ArrayList;

import model.GameData;
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
		info.add("o" + this.getName());
	}

	
	public void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
	}



}
