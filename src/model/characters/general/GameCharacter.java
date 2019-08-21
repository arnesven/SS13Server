package model.characters.general;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import graphics.OverlaySprite;
import graphics.sprites.PhysicalBody;
import graphics.sprites.OverlaySprites;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.*;
import model.actions.general.AttackAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.NoSuchInstanceException;
import model.characters.visitors.VisitorCharacter;
import model.items.BodyPartFactory;
import model.items.NoSuchThingException;
import model.items.foods.FoodItem;
import model.map.rooms.RoomType;
import model.map.rooms.SpaceRoom;
import model.movepowers.*;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import util.HTMLText;
import util.Logger;
import util.MyRandom;
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
import model.map.rooms.Room;

/**
 * @author erini02
 * Class for representing a character in the game. I.e. the physical representation
 * of a player or a NPC. 
 */
public abstract class GameCharacter implements SpriteObject, Serializable {
	
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
    private GameItem killerItem;
	private String gender;
    private PhysicalBody physBody;




    public GameCharacter(String name, int startRoom, double speed) {
		this.name = name;
		this.startingRoom = startRoom;
		this.speed = speed;
        gender = MyRandom.randomGender();
        physBody = new PhysicalBody(gender.equals("man"));
        killerItem = null;
	}

	/**
	 * @return the name of the character as it appears publicly
	 */
	public String getPublicName() {
		return getBaseName();
	}

	public String getPublicName(Actor whosAsking) {
	    return getPublicName();
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
                getActor().getCharacter().doUponDeath(performingClient, weapon);
			}

        }
        String critMess = doCritical(getActor(), weapon, critical);

        informAttackerOfAttack(success, performingClient, weapon, frag, critical, critMess);
        informActorOfAttack(success, performingClient, weapon, frag, critical, critMess);
        return success;
    }

    private String doCritical(Actor victim, Weapon weapon, boolean critical) {
	    double ampChance = weapon.getAmpChance();

	    String critMess = "";
        if (MyRandom.nextDouble() < ampChance) {
            if (critical) {
                String[] extremity = {"arm", "leg"};
                int rand = MyRandom.nextInt(2);
                String[] side = {"left", "right"};
                int rand2 = MyRandom.nextInt(2);
                String bodyPart = side[rand2] + " " + extremity[rand];
                victim.getCharacter().getPhysicalBody().removeBodyPart(bodyPart);
                victim.getPosition().addItem(BodyPartFactory.makeBodyPart(bodyPart, victim));
                critMess = "severed a" + (rand==0?"n ":" ") + extremity[rand] + "!";
            }
        } else {
            critMess = weapon.getCriticalMessage();
        }
        return critMess;
    }

    public void doUponDeath(Actor killer, GameItem killItem) {
        if (killItem != null) {
            this.killerItem = killItem;
        }

        if (killer instanceof Player && ((Player)killer).getSettings().get(PlayerSettings.AUTO_LOOT_ON_KILL)) {
            transferAllItemsTo(killer);
        } else {
            dropAllItems();
        }
        Logger.log(Logger.INTERESTING, getActor().getBaseName() + " just died! ");
        if (killer != null) {
            setKiller(killer);
            if (killerItem != null) {
                Logger.log(Logger.INTERESTING, "  ... killed by " + killer.getBaseName() + " with " + killerItem.getBaseName());
            }
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
        String msg = first + " " +
                verb + "ed " + second + " with " +
                weapon.getPublicName(getActor()) + ".";
        if (verb.equals("kill") && second.equals("you")) {
            msg = HTMLText.makeText("red", msg);
        }

        whom.addTolastTurnInfo(msg);
        if (weapon.wasCriticalHit()) {
            whom.addTolastTurnInfo(HTMLText.makeText("red", critMess + "!"));
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
       return  weapon.isAttackSuccessful(isWatching(getActor(), performingClient));
    }

    public static boolean isWatching(Actor watchingActor, Actor otherActor) {
		if (watchingActor instanceof Player) {
			Player watchingPlayer = (Player) watchingActor;
			if (watchingPlayer.getNextAction() instanceof WatchAction) {
				if (((WatchAction)watchingPlayer.getNextAction()).isArgumentOf(otherActor.getAsTarget())) {
					Logger.log(Logger.INTERESTING,
                            "Attack chance reduced because of watching...");
					return true;
				}
			}
		}
		return false;
	}

    public static boolean isAttacking(Actor attackingActor, Actor otherActor) {
        if (attackingActor instanceof NPC) {
            return false;
        }

        if (((Player)attackingActor).getNextAction() instanceof AttackAction) {
            if (((AttackAction)((Player)(attackingActor)).getNextAction()).isArgumentOf(otherActor.getAsTarget())) {
                Logger.log(Logger.INTERESTING,
                        "infect chance reduced because of attacking...");
                return true;
            }
        }
        return false;
    }

	public void beExposedTo(Actor something, Damager damager) {
		boolean reduced = false;
        Logger.log(this.getFullName() + " got hit by " + damager.getName());
        if (!isDead()) {
            getActor().addTolastTurnInfo(damager.getText());
        }

		if (damager.isDamageSuccessful(reduced)) {
			boolean wasDeadAlready = isDead();
            damager.doDamageOnMe(getActor().getAsTarget());
			if (this.isDead() && !wasDeadAlready) { // you died! Too bad!
                getActor().getCharacter().doUponDeath(something, damager.getOriginItem());
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

    private void transferAllItemsTo(Actor killer) {
        while (this.items.size() > 0) {
            killer.getCharacter().giveItem(this.items.remove(0), this.getActor().getAsTarget());
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

    public Actor getKiller() {
        return killer;
    }

	public boolean isCrew() {
		return false;
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

	public Room getStartingRoom(GameData gameData)  {
        try {
            return gameData.getRoomForId(startingRoom);
        } catch (NoSuchThingException e) {
            return MyRandom.sample(gameData.getRooms());
        }
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
			totalWeight += getSuit().getWeight();
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
        it.gotGivenTo(getActor(), giver);
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
        Sprite sp;
        if (suit == null) {
            sp = getActor().getCharacter().getNakedSprite();
		} else {
            sp = suit.getGetup(getActor(), whosAsking);
        }

        return sp;
    }

    public char getIcon(Player whosAsking) {
        return 'c';
    }

    public Sprite getNakedSprite() {
            return physBody.getSprite();
    }

    public void setNakedness(PhysicalBody nakedSprite) {
        this.physBody = nakedSprite;
    }

    public boolean isPassive() {
        return isDead();
    }

    public boolean getsActions() {
        return !isDead();
    }

    public boolean isVisible() {
        return true;
    }

    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        if (getActor() instanceof  Player && ((Player) getActor()).getSettings().get(PlayerSettings.CURRENT_ROOM_STUFF_IN_MAP)) {
            return OverlaySprites.normalVision(((Player)getActor()), gameData, getPosition());
        }
        return OverlaySprites.dummyList();
    }

    public void doBeforeMovement(GameData gameData) {

    }

    public void doAfterMovement(GameData gameData) {

    }

    public void doAfterActions(GameData gameData) {

    }

    public void doAtEndOfTurn(GameData gameData) {

    }
    public List<Room> getVisibleMap(GameData gameData) {
        String level = "ss13";
        try {
            level = gameData.getMap().getLevelForRoom(getPosition());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        Collection<Room> roomsToShow = gameData.getMap().getRoomsForLevel(level);
        roomsToShow.removeIf((Room r ) -> r.getType() == RoomType.hidden);


        List<Room> result = new ArrayList<>();
        if (getActor() instanceof  Player && !(getActor().isDead() ||
                getActor().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof VisitorCharacter))) {
            int[] moveableToRooms = ((Player) getActor()).getSelectableLocations(gameData);
            int maxX = 0;
            int maxY = 0;
            int minY = 1000000;
            int minX = 1000000;

            for (Room r : roomsToShow) {
                if (r.getX()+r.getWidth() > maxX) {
                    maxX = r.getX();
                }
                if (r.getY()+r.getHeight() > maxY) {
                    maxY = r.getY();
                }
                if (r.getX() < minX) {
                    minX = r.getX();
                }
                if (r.getY() < minY) {
                    minY = r.getY();
                }

                for (int i = 0; i < moveableToRooms.length; ++i) {
                    if (r.getID() == moveableToRooms[i]) {
                        result.add(r);
                        break;
                    }
                }
            }



        } else {
            result.addAll(roomsToShow);
        }

   //     getMovePowersIfPlayer(gameData, result);



        return result;
    }

//    public void getMovePowersIfPlayer(GameData gameData, List<Room> result) {
//        if (getActor() instanceof Player && ((Player) getActor()).getSettings().get(PlayerSettings.ACTIVATE_MOVEMENT_POWERS)) {
//            MovePowersHandler mp = new MovePowersHandler(result, 2);
//            addMovepowersButtons(result, gameData, mp);
//        }
//
//        if (getActor() instanceof  Player && ((Player) getActor()).getSettings().get(PlayerSettings.STYLE_BUTTONS_ON)) {
//            result.clear();
//            result.add(getPosition());
//            MovePowersHandler mp = new MovePowersHandler(result, 1);
//            addStyleMovementButtons(result, gameData, mp);
//        }
//    }


    private void addStyleMovementButtons(List<Room> result, GameData gameData, MovePowersHandler mp) {
//	    for (int i = 0; i < PhysicalBody.noOfHumans(); ++i) {
//	        result.add(mp.makeButton(new SetNakedHumanPower(i)));
//        }

	    for (int i = 0; i < PhysicalBody.noOfHairs(); ++i) {
            result.add(mp.makeButton(new SetHairMovePower(i)));
        }
        for (int i = 0; i < PhysicalBody.noOfFacials(); ++i) {
	        result.add(mp.makeButton(new SetFacialHairMovePower(i)));
        }
        for (Color col : SetHairColorPower.getHairColors()) {
	        result.add(mp.makeButton(new SetHairColorPower(col)));
        }
    }

    public void addMovepowersButtons(List<Room> result, GameData gameData, MovePowersHandler mp) {

	    result.add(mp.makeButton(new SearchMovePower()));
	    result.add(mp.makeButton(new RepeatMovePower()));
	    MovePower moveP = new HealyourselfMovePower();
	    if (moveP.isApplicable(gameData, getActor())) {
            result.add(mp.makeButton(moveP));
        }

        moveP = new FollowMovePower();
        if (moveP.isApplicable(gameData, getActor())) {
            result.add(mp.makeButton(moveP));
        }
	    result.add(mp.makeButton(new BlockMovement()));
        if (getsActions() && !isPassive()) {
            result.add(mp.makeButton(new AutoAttack()));
        }
    }


    public void addActionsForActorsInRoom(GameData gameData, Actor otherActor,
                                          ArrayList<Action> at) {
    }

    public void doWhenConsumeItem(FoodItem foodItem, GameData gameData) {

    }

    public void moveDecoratorsAndCopy(GameCharacter selectedJob) {
        selectedJob.maxHealth = this.maxHealth;
        //selectedJob.name = this.name;
        selectedJob.startingRoom = this.startingRoom;
        selectedJob.health = this.health;
        selectedJob.actor = this.actor;
        selectedJob.position = this.position;
        //selectedJob.speed = this.speed;
        selectedJob.items = this.items;
        //selectedJob.suit = this.suit;
        selectedJob.killer = this.killer;
        selectedJob.killerItem = this.killerItem;
        selectedJob.killString = this.killString;
        selectedJob.gender = this.gender;
        selectedJob.physBody = this.physBody;
    }

    public GameItem getKillerItem() {
        return killerItem;
    }

    public void modifyActionList(GameData gameData, ArrayList<Action> at) { };

    public ActionBehavior getDefaultActionBehavior() {
        return new DoNothingBehavior();
    }

    public boolean isVisibileFromAdjacentRoom() {
        return false;
    }

    public PhysicalBody getPhysicalBody() {
        return physBody;
    }

    public CharacterDecorator getDecorator(InstanceChecker check) {
        throw new NoSuchInstanceException("Can't call getDecorator on GameCharacter");
    }


    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        return new ArrayList<>();
    }
}
