package model.actions;

import model.Client;
import model.GameData;
import model.map.Room;
import model.npcs.NPC;

public interface ActionPerformer {

	Room getPosition();

	void addTolastTurnInfo(String string);
	
	boolean isInfected();

	String getPublicName();

	boolean isClient(Client cl);

	boolean isNPC(NPC npc);

	Target getAsTarget();
}
