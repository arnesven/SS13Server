package model.npcs;

import java.util.ArrayList;

import model.GameData;
import model.actions.ActionPerformer;
import model.actions.Target;
import model.characters.GameCharacter;
import model.items.Weapon;
import model.map.Room;

/**
 * @author erini02
 * Class representing NPCs on the station (non-player characters).
 * E.g. monsters, crewmembers not controlled by players. And other things.
 */
public class NPC implements Target {
	private GameCharacter chara;
	private MovementBehavior moveBehavior;
	private ActionBehavior actBehavior;
	private Room position = null;
	private double maxHealth;
	
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
		if (!isDead()) {
			moveBehavior.move(this);
		}
	}


	public void actAccordingToBehavior(GameData gameData) {
		if (!isDead()) {
			actBehavior.act(this, gameData);
		}
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

	/**
	 * Gets the character of this NPC.
	 * @return the character of this NPC.
	 */
	public GameCharacter getCharacter() {
		return chara;
	}
	
	public boolean isInfected() {
		//TODO: NPCs should be able to be infected
		return false;
	}

	@Override
	public boolean isTargetable() {
		return !isDead();
	}

	@Override
	public void beAttackedBy(ActionPerformer performingClient, Weapon item) {
		getCharacter().beAttackedBy(performingClient, item);
	}

	@Override
	public boolean isDead() {
		return getCharacter().isDead();
	}

	public boolean shouldBeCleanedUp() {
		return false;
	}

	@Override
	public double getHealth() {
		return getCharacter().getHealth();
	}

	@Override
	public double getMaxHealth() {
		return this.maxHealth;
	}
	
	public void setMaxHealth(double d) {
		this.maxHealth = d;
	}
	
	public void setHealth(double d) {
		getCharacter().setHealth(d);
	}
	
	
}
