package model.actions;

import model.Client;
import model.GameData;
import model.map.Room;

public interface ActionPerformer {

	Room getPosition();

	void addTolastTurnInfo(String string);
	
	boolean isInfected();

	String getPublicName();

	boolean isClient(Client cl);

}
