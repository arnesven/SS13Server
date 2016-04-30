package model.characters.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.NakedHumanSprite;
import graphics.sprites.Sprite;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.actions.general.WatchAction;
import model.characters.decorators.InstanceChecker;
import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;
import model.map.Room;

/**
 * @author erini02
 * Class for representing a character in the game. I.e. the physical representation
 * of a player or a NPC. 
 */
public abstract class GameCharacter implements Serializable {
	
	private double maxHealth = 3.0;
	
	private static final double ENCUMBERANCE_LEVEL = 5.0;
	private String name;
	private int startingRoom = 0;
	private double health = 3.0;
	private Actor actor = null;
	private Room position = null;
	private double speed;
	private List<GameItem> items = new ArrayList<>();
	private SuitItem suit;
	private Actor killer;
	private String killString;
	private String gender;
    private Sprite nakedSprite;


    public GameCharacter(String name, int startRoom, double speed) {
		this.name = name;
		this.startingRoom = startRoom;
		this.speed = speed;
        gender = MyRandom.randomGender();
        nakedSprite = new NakedHumanSprite(gender.equals("man"));
	}

	/**
	 * @return the name of the character as it appears publicly
	 */
	public String getPublicName() {
		return getBaseName();
	}
	

	/**
	 * Gets the name of this character, for instance "Captain" or "Doctor"
	 * @return the base name of the character
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
		boolean wasDeadAlready = isDead();
		boolean success = getActor().getCharacter().isAttackerSuccessful(performingClient, weapon);
        boolean frag = false;
        boolean critical = false;
        if (success) {
			critical = getActor().getCharacter().doDamageOnSelfWith(weapon);

			if (this.isDead() && !wasDeadAlready) { // you died! Too bad!
				frag = true;
                getActor().getCharacter().doUponDeath(performingClient);
			}

        }
        String critMess = weapon.getCriticalMessage();
        informAttackerOfAttack(success, performingClient, weapon, frag, critical, critMess);
        informActorOfAttack(success, performingClient, weapon, frag, critical, critMess);
        return success;
    }

    public void doUponDeath(Actor killer) {
        dropAllItems();
        if (killer != null) {
            setKiller(killer);
        }
    }

    private void internalAttackSuccess(Actor whom,
                                       Weapon weapon, boolean frag, boolean crit,
                                       String first, String second, String critMess) {
        String verb = weapon.getSuccessfulMessage();
        if (frag) {
            verb = "kill";
            second = second.replace(" (dead)", "");
        }
        whom.addTolastTurnInfo(first + " " +
                verb + "ed " + second + " with " +
                weapon.getPublicName(getActor()) + ".");
        if (weapon.wasCriticalHit()) {
            whom.addTolastTurnInfo(weapon.getCriticalMessage() + "!");
        }

    }

    private void informActorOfAttack(boolean success, Actor performingClient, Weapon weapon, boolean frag, boolean crit, String critMess) {
        if (success) {
            internalAttackSuccess(getActor(), weapon, frag, crit, performingClient.getPublicName(), "you", critMess);
        } else {
            getActor().addTolastTurnInfo(performingClient.getPublicName() + " tried to " +
                    weapon.getSuccessfulMessage() + " you with " +
                    weapon.getPublicName(getActor()) + ".");
        }
    }

    private void informAttackerOfAttack(boolean success, Actor performingClient, Weapon weapon, boolean frag, boolean crit, String critMess) {
        if (success) {
            internalAttackSuccess(performingClient, weapon, frag, crit, "You", getActor().getPublicName(), critMess);
        } else {
            performingClient.addTolastTurnInfo("Your attacked missed!");
        }
    }


    public boolean doDamageOnSelfWith(Weapon weapon) {
        if (!getActor().getCharacter().wasCriticalHit(weapon)) {
            Logger.log(" ... not a critical hit. Doing damage..");
            weapon.dealDamageOnMe(getActor());
            return false;
        }
        Logger.log(Logger.INTERESTING, " --> Critical Hit!");
        weapon.dealCriticalDamageOnMe(getActor());
        return true;
    }

    public boolean wasCriticalHit(Weapon weapon) {
        return weapon.wasCriticalHit();
    }


    public boolean isAttackerSuccessful(Actor performingClient, Weapon weapon) {
       return  weapon.isAttackSuccessful(isReduced(getActor(), performingClient));
    }

    public boolean isReduced(Actor thisActor, Actor performingClient) {
		if (thisActor instanceof Player) {
			Player thisClient = (Player) thisActor;
			if (thisClient.getNextAction() instanceof WatchAction) {
				if (((WatchAction)thisClient.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
					Logger.log(Logger.INTERESTING,
                            "Attack chance reduced because of watching...");
					return true;
				}
			}
		}
		return false;
	}

	public void beExposedTo(Actor something, Damager damager) {
		boolean reduced = false;
        Logger.log(this.getFullName() + " got hit by " + damager.getName());
		getActor().addTolastTurnInfo(damager.getText());

		if (damager.isDamageSuccessful(reduced)) {
			boolean wasDeadAlready = isDead();
            damager.doDamageOnMe(getActor().getAsTarget());
			if (this.isDead() && !wasDeadAlready) { // you died! Too bad!
                getActor().getCharacter().doUponDeath(something);
                if (something == null) {
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

	public Actor getActor() {
		return actor;
	}
	
	public void setActor(Actor c) {
		this.actor = c;
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
		if (s.sound == AudioLevel.SAME_ROOM ||
                s.sound == AudioLevel.VERY_LOUD ||
                s.visual == VisualLevel.CLEARLY_VISIBLE) {
			return true;
		}

        return (s.smell != OlfactoryLevel.UNSMELLABLE);
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
		} else if (getActor().getCharacter().isEncumbered() ||
                getActor().getCharacter().getHealth() <= 1.0) {
			return 1;
		}
		return 2;
	}

	public boolean isEncumbered() {
		 return getTotalWeight() >= getEncumberenceLevel();
	}

	public double getEncumberenceLevel() {
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
	
	public void setGender(String gen) {
		this.gender = gen;
	}

	/**
	 * @param it the item being given
	 * @param giver, can be null be careful
	 */
	public void giveItem(GameItem it, Target giver) {
		this.getItems().add(it);
        it.gotGivenTo(this, giver);
		it.setHolder(this);
	}

	public String getHowPerceived(Actor actor) {
		return actor.getPublicName();
	}

	public Weapon getDefaultWeapon() {
		return Weapon.FISTS;
	}


	public String getWatchString(Actor whosAsking) {
		String healthStr = "healthy";
		if (this.isDead()) {
			healthStr = "dead";
		} else if (this.getHealth() < this.getMaxHealth() ){
			healthStr = "unhealthy";
		}
		
		String itemStr = null;
		String name = this.getActor().getBaseName();

		if (this.getItems().size() > 0) {
			GameItem randomItem = MyRandom.sample(getItems());
			itemStr = " and is carrying a " + randomItem.getPublicName(whosAsking);
		}

		return name + " looks " + healthStr + (itemStr==null?"":itemStr) + ".";
	}


	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(double d) {
		maxHealth = d;
	}
	
	public void printInstances() {
		Logger.log(Logger.CRITICAL,
                this.getClass().getName());
	}
	
	public abstract GameCharacter clone();

	public boolean hasInventory() {
		return true;
	}

	public boolean canUseObjects() {
		return true;
	}

    public boolean getsAttackOfOpportunity(Weapon w) {
        return w.givesAttackOfOpportunity();
    }

    public Sprite getSprite(Actor whosAsking) {
        if (suit == null) {

            return getNakedSprite();
		} else {
            return suit.getGetup(getActor(), whosAsking);
        }
    }

    public char getIcon(Player whosAsking) {
        return 'c';
    }

    public Sprite getNakedSprite() {
            return nakedSprite;
    }

    public void setNakedSprite(Sprite nakedSprite) {
        this.nakedSprite = nakedSprite;
    }

    public boolean isPassive() {
        return isDead();
    }

    public boolean getsActions() {
        return !isDead();
    }
}