package model.characters;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.actions.WatchAction;
import model.characters.decorators.InstanceChecker;
import model.events.Damager;
import model.events.Event;
import model.events.RadiationStorm;
import model.items.GameItem;
import model.items.KeyCard;
import model.items.MedKit;
import model.items.suits.Clothes;
import model.items.suits.OutFit;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;
import model.map.Room;

/**
 * @author erini02
 * Class for representing a character in the game. I.e. the physical representation
 * of a player or a NPC. 
 */
public abstract class GameCharacter {
	
	private static final double ENCUMBERANCE_LEVEL = 5.0;
	private String name;
	private int startingRoom = 0;
	private double health = 3.0;
	private Player client = null;
	private Room position = null;
	private double speed;
	private List<GameItem> items = new ArrayList<>();
	private SuitItem suit;
	private Actor killer;
	private String killString;
	private String gender = MyRandom.randomGender();


	
	public GameCharacter(String name, int startRoom, double speed) {
		this.name = name;
		this.startingRoom = startRoom;
		this.speed = speed;
		suit = new OutFit(name);
	}

	/**
	 * @return the name of the character as it appears publicly
	 */
	public String getPublicName() {
		String res = name;
		if (suit == null) {
			res = "Naked " + getGender() ;
		}
		if (isDead()) {
			return res + " (dead)";
		}
		return res;
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
		return getBaseName() + (isDead()?" (dead)":"");
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

	
	public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
		Player thisClient  = this.getClient();
		boolean success = false;
		boolean reduced = false;
		if (thisClient != null) {
			if (thisClient.getNextAction() instanceof WatchAction) {
				if (((WatchAction)thisClient.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
					System.out.println("Attack chance reduced because of watching...");
					reduced = true;
				}
			}
		}
		boolean wasDeadAlready = isDead();
		if (weapon.isAttackSuccessful(reduced)) {
			success = true;
			health = Math.max(0.0, health - weapon.getDamage());
			
			String verb = weapon.getSuccessfulMessage();
			if (this.isDead() && !wasDeadAlready) { // you died! Too bad!
				verb = "kill";
				dropAllItems();
				setKiller(performingClient);
			}
			
			performingClient.addTolastTurnInfo("You " + verb + "ed " + 
											   getPublicName() + " with " + weapon.getPublicName(performingClient) + ".");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getPublicName() + " " + 
											 verb + "ed you with " + 
											 weapon.getPublicName(thisClient) + "."); 
			}
			
		} else {
			performingClient.addTolastTurnInfo("Your attacked missed!");
			if (thisClient != null) {
				thisClient.addTolastTurnInfo(performingClient.getPublicName() + " tried to " + 
											 weapon.getSuccessfulMessage() + " you with " + 
											 weapon.getPublicName(thisClient) + "."); 
			}
		}
		
		return success;
		
	}

	public void beExposedTo(Actor something, Damager damager) {
		boolean reduced = false;
		if (getClient() != null) {
			getClient().addTolastTurnInfo(damager.getText());
		}
		if (damager.isDamageSuccessful(reduced)) {
			boolean wasDeadAlready = isDead();
			health = Math.max(0.0, health - damager.getDamage());
			if (this.isDead() && !wasDeadAlready) { // you died! Too bad!
				dropAllItems();
				if (something != null) {
					setKiller(something);
				} else {
					killString = damager.getName();
				}
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


	/**
	 * Overload this method if you want a character
	 * to have specific reaction when interacted with a certain item.
	 * @return true if the character had a specific reaction, false otherwise
	 */
	public boolean hasSpecificReaction(GameItem it) {
		return false;
	}

	public List<GameItem> getItems() {
		return items;
	}

	private Actor getKiller() {
		return killer;
	}
	
	public String getKillerString() {
		if (killer != null) {
			return killer.getBaseName();
		}
		return killString;
	}
	
	public void setKiller(Actor a) {
		this.killer = a;
	}

	public boolean isCrew() {
		return true;
	}

	public boolean doesPerceive(Action a) {
		SensoryLevel s = a.getSense();
		if (s.sound == AudioLevel.SAME_ROOM || s.sound == AudioLevel.VERY_LOUD) {
			return true;
		}
		if (s.visual == VisualLevel.CLEARLY_VISIBLE) {
			return true;
		}
		if (s.smell != OlfactoryLevel.UNSMELLABLE) {
			return true;
		}
		
		return false;
	}

	public boolean isHealable() {
		return true;
	}

	public Room getStartingRoom(GameData gameData) {
		return gameData.getRoomForId(startingRoom);
	}

	public int getMovementSteps() {
		if (isDead()) {
			return 0;
		} else if (isEncumbered() || getHealth() <= 1.0) {
			return 1;
		}
		return 2;
	}

	public boolean isEncumbered() {
		if (getTotalWeight() >= getEncumberenceLevel()) {
			return true;
		}
		
		return false;
	}

	public double getEncumberenceLevel() {
		System.out.println("Running encumberence for " + getBaseName());
		return ENCUMBERANCE_LEVEL;
	}

	public double getTotalWeight() {
		double totalWeight = 0.0;
		for (GameItem it : getItems()) {
			totalWeight += it.getWeight();
		}
		if (getSuit() != null) {
			totalWeight += suit.getWeight();
		}
		return totalWeight;
	}

	public SuitItem getSuit() {
		return suit;
	}

	public void putOnSuit(SuitItem gameItem) {
		gameItem.setUnder(this.suit);
		this.suit = gameItem;
	}

	public void removeSuit() {
		if (this.suit != null) {
			SuitItem underSuit = suit.getUnder();
			this.suit.setUnder(null);
			this.suit = underSuit;
		}
	}

	/**
	 * Either "man" or "woman"
	 * @return the characters gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param it the item being given
	 * @param giver, can be null be careful
	 */
	public void giveItem(GameItem it, Target giver) {
		this.getItems().add(it);
		it.setHolder(this);
	}

	public String getHowPerceived(Actor actor) {
		return actor.getPublicName();
	}


	

}
