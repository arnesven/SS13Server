package model.objects.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.map.Room;

public class GameObject {
	private String name;
	private Room position;
	
	public GameObject(String name, Room position) {
		this.name = name;
		this.position = position;
	}

	public String getBaseName() {
		return name;
	}
	
	public String getPublicName(Actor whosAsking) {
		return getBaseName();
	}
	
	public Room getPosition() {
		return position;
	}
	

	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		info.add(getIcon(whosAsking) + this.getPublicName(whosAsking));
	}

	
	protected char getIcon(Player whosAsking) {
		return 'o';
	}

	public void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
	}

	


}
