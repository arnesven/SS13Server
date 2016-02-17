package model.npcs;

import java.util.ArrayList;

import model.GameData;
import model.characters.GameCharacter;
import model.map.Room;

/**
 * @author erini02
 * Class representing NPCs on the station (non-player characters).
 * E.g. monsters, crewmembers not controlled by players. And other things.
 */
public class NPC {
	private GameCharacter chara;
	private MovementBehavior moveBehavior;
	private ActionBehavior actBehavior;
	private Room position = null;
	
	public NPC(GameCharacter chara, MovementBehavior m, ActionBehavior a, Room r) {
		this.chara = chara;
		moveBehavior = m;
		actBehavior = a;
		moveIntoRoom(r);
	}
	
	protected void moveIntoRoom(Room r) {
		if (position != null) {
			position.removeNPC(this);
		}
		r.addNPC(this);
		position = r;
	}

	public void moveAccordingToBehavior() {
		moveBehavior.move(this);
	}


	public void actAccordingToBehavior(GameData gameData) {
		actBehavior.act(this, gameData);
	}

	public void addYourselfToRoomInfo(ArrayList<String> info) {
		info.add(chara.getPublicName());
		
	}

	public Room getPosition() {
		return position;
	}

	public String getName() {
		return chara.getFullName();
	}

	public boolean isInfected() {
		// TODO implement this correctly
		return false;
	}
	
	
}
