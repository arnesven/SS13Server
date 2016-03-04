package model.npcs;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Target;
import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.MedKit;
import model.items.Weapon;
import model.map.Room;

/**
 * @author erini02
 * Class representing NPCs on the station (non-player characters).
 * E.g. monsters, crewmembers not controlled by players. And other things.
 */
public class NPC extends Actor implements Target {
	
	private GameCharacter chara;
	
	private MovementBehavior moveBehavior;
	private ActionBehavior actBehavior;
	private double maxHealth;
	
	public NPC(GameCharacter chara, MovementBehavior m, ActionBehavior a, Room r) {
		this.chara = chara;
		moveBehavior = m;
		actBehavior = a;
		moveIntoRoom(r);
	}
	
	protected void moveIntoRoom(Room r) {
		if (getPosition() != null) {
			getPosition().removeNPC(this);
		}
		r.addNPC(this);
		setPosition(r);
	}

	private void setPosition(Room r) {
		chara.setPosition(r);
	}
	
	public Room getPosition() {
		return chara.getPosition();
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
		info.add("a" + chara.getPublicName());
		
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
	public void beAttackedBy(Actor performingClient, Weapon item) {
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

	@Override
	public void addTolastTurnInfo(String string) {
		// Not needed for NPCs
	}

	@Override
	public String getPublicName() {
		return getCharacter().getPublicName();
	}

	@Override
	public Target getAsTarget() {
		return this;
	}

	@Override
	public List<GameItem> getItems() {
		return chara.getItems();
	}
	
	@Override
	public double getSpeed(){
		return getCharacter().getSpeed();
	}
	
	@Override
	public void action(GameData gameData) {
		this.actAccordingToBehavior(gameData);
	}

	@Override
	public boolean isHuman() {
		return getCharacter().isHuman();
	}

	@Override
	public boolean hasSpecificReaction(MedKit objectRef) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addToHealth(double d) {
		getCharacter().setHealth(Math.min(getMaxHealth(), 
				 getCharacter().getHealth() + d));
	}

	@Override
	public String getBaseName() {
		return chara.getBaseName();
	}
}
