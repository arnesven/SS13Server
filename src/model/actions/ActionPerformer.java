package model.actions;

import java.util.List;

import model.Client;
import model.GameData;
import model.items.GameItem;
import model.map.Room;
import model.npcs.NPC;

public interface ActionPerformer  {

	Room getPosition();

	void addTolastTurnInfo(String string);
	
	boolean isInfected();

	String getPublicName();

	Target getAsTarget();

	List<GameItem> getItems();

	double getSpeed();

	void action(GameData gameData);
}
