package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.actions.Action;
import model.actions.WatchAction;
import model.items.GameItem;
import model.items.KeyCard;
import model.items.MedKit;
import model.items.Weapon;
import model.map.Room;

/**
 * @author erini02
 * Class for representing a character in the game. I.e. the physical representation
 * of a player or a NPC. 
 */
public abstract class GameCharacter {
	
	private String name;
	private int startingRoom = 0;
	private double health = 3.0;
	private Player client = null;
	private Room position = null;
	private double speed;
	private List<GameItem> items = new ArrayList<>();
	private Actor killer;

	
	public GameCharacter(String name, int startRoom, double speed) {
		this.name = name;
		this.startingRoom = startRoom;
		this.speed = speed;
	}

	/**
	 * @return the name of the character as it appears publicly
	 */
	public String getPublicName() {
		return name + (isDead()?" (dead)":"");
	}
	
	/**
	 * Gets the name of this character, for instance "Captain" or "Doctor"
	 * @return
	 */
	public String getBaseName() {
		return name;
	}

	/**
	 * @return the room in which the character starts
	 */
	public int getStartingRoom() {
		return startingRoom;
	}

	/**
	 * @return the name of the character as it appears for anyone knowing the TRUTH.
	 */
	public String getFullName() {
		return getBaseName();
	}

	public boolean isDead() {
		return health <= 0.0;
	}

	/**
	 * @return true if this character can be targeted by attacks and interacted with through actions
	 */
	public boolean isInteractable() {
		return !isDead();
	}

	
	public void beAttackedBy(Actor performingClient, Weapon weapon) {
		Player thisClient  = this.getClient();
		boolean reduced = false;
		if (thisClient != null) {
			if (thisClient.getNextAction() instanceof WatchAction) {
				if (((WatchAction)thisClient.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
					System.out.println("Attack chance reduced because of watching...");
					reduced = true;
				}
			}
		}
		
		if (weapon.isAttackSuccessful(reduced)) {
			health = Math.max(0.0, health - weapon.getDamage());
			String verb = weapon.getSuccessfulMessage();
			if (this.isDead()) { // you died! Too bad!
				verb = "kill";
				dropAllItems();
				setKiller(performingClient);
			}
			
			performingClient.addTolastTurnInfo("You " + verb + "ed " + 
											   getBaseName() + " with " + weapon.getName() + ".");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getPublicName() + " " + 
											 verb + "ed you with " + 
											 weapon.getName() + "."); 
			}
			
		} else {
			performingClient.addTolastTurnInfo("Your attacked missed!");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getPublicName() + " tried to " + 
											 weapon.getSuccessfulMessage() + " you with " + 
											 weapon.getName() + "."); 
			}
		}
		
	}

	private void dropAllItems() {
		while (this.items.size() > 0) {
			this.position.addItem(this.items.remove(0));
		}
	}

	public Player getClient() {
		return client;
	}
	
	public void setClient(Player c) {
		this.client = c;
	}

	/**
	 * Gets the health of this character.
	 * @return the health of this character.
	 */
	public double getHealth() {
		return health;
	}

	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) { 
		// No specific actions for ordinary characters...
	}

	public Room getPosition() {
		return position ;
	}

	public void setPosition(Room room) {
		position = room;
	}

	public boolean checkInstance(InstanceChecker infectChecker) {
		return infectChecker.checkInstanceOf(this);
	}

	public void setHealth(double d) {
		this.health = d;
	}

	public double getSpeed() {
		return speed;
	}

	public abstract List<GameItem> getStartingItems();

	public boolean isHuman() {
		return true;
	}

	/**
	 * Overload this method if you want a character
	 * to have specific reaction when interacted with a certain item.
	 * @return true if the character had a specific reaction, false otherwise
	 */
	public boolean hasSpecificReaction() {
		return false;
	}

	public List<GameItem> getItems() {
		return items;
	}

	public Actor getKiller() {
		return killer;
	}
	
	public void setKiller(Actor a) {
		this.killer = a;
	}

}
