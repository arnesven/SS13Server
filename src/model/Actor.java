package model;

import java.io.Serializable;
import java.util.*;

import model.actions.general.*;
import model.characters.general.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InfectedCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.NoSuchInstanceException;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import util.Logger;

public abstract class Actor  implements ItemHolder, Serializable {

	private GameCharacter character = null;
	
	public abstract void addTolastTurnInfo(String string);
	public abstract Target getAsTarget();
	public abstract void action(GameData gameData);
	public abstract void beInfected(Actor performingClient);


	
	/**
	 * Returns the character of this Actor,
	 * or null if one has not yet been set.
	 * @return the Actor's character
	 */
	public GameCharacter getCharacter() {
		return character;
	}

    public boolean beAttackedBy(Actor performingClient, Weapon item) {
        boolean succ = getCharacter().beAttackedBy(performingClient, item);
        if (item.hasRealSound()) {
            if (this instanceof Player) {
                ((Player)this).getSoundQueue().add(item.getRealSound());
            }

            if (performingClient instanceof Player) {
                ((Player)performingClient).getSoundQueue().add(item.getRealSound());
            }
        }
        return succ;
    }
	
	/**
	 * Sets the character for this Actor.
	 * This will set the character for the Actor.
	 * @param charr the new character
	 */
	public void setCharacter(GameCharacter charr) {
		this.character = charr;
		getCharacter().setActor(this);
//		if (charr instanceof CharacterDecorator) {
//			charr.printInstances();
//		}
	}
	
	/**
	 * Gets the current position of this Actor's character.
	 * I.e. what room this actor's character is currently in.
	 * @return the room of the actor's character.
	 */
	public Room getPosition() {
		return getCharacter().getPosition();
	}

	/**
	 * Sets the current position (room) of this actor's character
	 * @param room the new position
	 */
	public void setPosition(Room room) {
		getCharacter().setPosition(room);
	}
	
	/**
	 * Returns wether or not this client has been infected.
	 * This starts to check the instance of the character and
	 * its inner characters.
	 * @return true if this client's character is infected. False otherwise.
	 * @throws IllegalStateException if the client's character has not yet been set.
	 */
	public boolean isInfected() {
		if (getCharacter() == null) {
			throw new IllegalStateException("This actor's character has not yet been set.");
		}
		
		InstanceChecker infectChecker = new InstanceChecker() {
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof InfectedCharacter;
			}
		};
		
		return getCharacter().checkInstance(infectChecker);
	}

	public String getPublicName() {
		return getCharacter().getPublicName();
	}

	/**
	 * Gets the base name of the character of this actor.
	 * The base name is the name of the character in its simplest
	 * form, e.g. "Doctor" or "Chaplain".
	 * @return the base name of the Actor.
	 */
	public String getBaseName() {
		return getCharacter().getBaseName();
	}
	
	/**
	 * Gets the items of an actor as a list
	 * @return the list of game items.
	 */
	public List<GameItem> getItems() {
		return getCharacter().getItems();
	}

	/**
	 * Adds an item to this player.
	 * @param it the item to be added.
	 * @param giver 
	 */
	public void addItem(GameItem it, Target giver) {
		getCharacter().giveItem(it, giver);
	}

	/**
	 * Gets the speed of this actor's character.
	 * The speed determines in what order actions are executed.
	 * @return the speed of the actor's character.
	 */
	public double getSpeed() {
		return getCharacter().getSpeed();
	}
	

	/**
	 * Adds the player's character's public name to the room info.
	 * @param info the info to be added to.
	 * @param whosAsking 
	 */
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		if ( whosAsking == this) {
            info.add(getCharacter().getSprite(whosAsking).getName() + "<img>" + "You" + (getCharacter().isVisible()?"":" (invisible)"));
        } else if (getCharacter().isVisible()){
            info.add(getCharacter().getSprite(whosAsking).getName() + "<img>" + whosAsking.getCharacter().getHowPerceived(this));
        }
    }
	
	
	public boolean isDead() {
		return getCharacter().isDead();
	}

	public void removeInstance(InstanceChecker check) {
		if (getCharacter() instanceof CharacterDecorator) {
			CharacterDecorator decor = (CharacterDecorator)getCharacter();
			if (check.checkInstanceOf(decor)) {
				this.setCharacter(decor.getInner());
			} else {
				decor.removeInstance(check);
			}
			
		} else {
		    throw new NoSuchInstanceException("Could not remove instance!");
		}
	}
	
	public void putOnSuit(SuitItem selectedItem) {
		this.getCharacter().putOnSuit(selectedItem);
		selectedItem.beingPutOn(this);	
	}
	
	public void takeOffSuit() {
		SuitItem item = getCharacter().getSuit();
		this.getCharacter().removeSuit();
		item.beingTakenOff(this);
	}

    public void addToHealth(double d) {
        getCharacter().setHealth(Math.min(getMaxHealth(),
                getCharacter().getHealth() + d));
    }

    public void subtractFromHealth(double d) {
        getCharacter().setHealth(Math.max(0.0,
                getCharacter().getHealth() - d));
    }

    /**
     * Gets the tree structure of selectable actions from which the player
     * can select one. Some actions require subinformation, which is why this
     * datastructure is a tree.
     * @param gameData the Game's data
     * @return the tree of actions.
     */
    public ArrayList<Action> getActionList(GameData gameData) {
        ArrayList<Action> at = new ArrayList<Action>();

        if (getsActions()) {
            addBasicActions(at);

            addRoomActions(gameData, at);

            addAttackActions(at);
            addWatchAction(at);
            if (this.hasInventory()) {
                addManageItemActions(gameData, at);
            }

            getCharacter().addCharacterSpecificActions(gameData, at);

        }

        groupTargetingActions(at);
        at.add(0, new DoNothingAction());

        return at;
    }

    public boolean getsActions() {
        if (getCharacter() != null) {
            return getCharacter().getsActions();
        }
        return !isDead();
    }

    public boolean hasInventory() {
        return getCharacter().hasInventory();
    }

    private void groupTargetingActions(ArrayList<Action> at) {
        List<Action> targetingActions = new ArrayList<>();
        Iterator<Action> it = at.iterator();
        while (it.hasNext()) {
            Action a = it.next();
            if (a instanceof TargetingAction) {
                targetingActions.add(a);
                it.remove();
            }
        }
        if (targetingActions.size() > 0) {
            ActionGroup ag = new ActionGroup("Interaction");
            ag.addAll(targetingActions);
            at.add(ag);
        }
    }

    private void addManageItemActions(GameData gameData, ArrayList<Action> at2) {
        ArrayList<Action> at = new ArrayList<>();
        addGiveAction(at);
        addDropActions(gameData, at);
        addPickUpActions(gameData, at);
        addPutOnActions(at);
        addItemActions(gameData, at);
        if (at.size() > 0) {
            ActionGroup manageItems = new ActionGroup("Items");
            manageItems.addAll(at);
            at2.add(manageItems);
        }
    }

    private void addItemActions(GameData gameData, ArrayList<Action> at) {
        Set<String> set = new HashSet<>();
        ArrayList<Action> itActions = new ArrayList<>();
        for (GameItem it : getItems()) {
            if (!set.contains(it.getFullName(this))) {
                it.addYourActions(gameData, itActions, this);
                set.add(it.getFullName(this));
            }
        }


        for (GameItem it : getPosition().getItems()) {
            if (it.isUsableFromFloor()) {
                it.addYourActions(gameData, itActions, this);
            }
        }

        if (itActions.size() > 0) {
            ActionGroup ag = new ActionGroup("Use");
            ag.addAll(itActions);
            at.add(ag);
        }
    }

    private void addPickUpActions(GameData gameData, ArrayList<Action> at) {
        PickUpAction pickUpAction = new PickUpAction(this);
        if (pickUpAction.getOptions(gameData, this).numberOfSuboptions() > 0) {
            at.add(pickUpAction);
        }
    }

    private void addDropActions(GameData gameData, ArrayList<Action> at) {
        DropAction dropAction = new DropAction(this);
        if (dropAction.getOptions(gameData, this).numberOfSuboptions() > 0) {
            at.add(dropAction);
        }
    }

    private void addRoomActions(GameData gameData, ArrayList<Action> at) {
        ActionGroup roomActions = new ActionGroup("Room");

        ArrayList<Action> at2 = new ArrayList<>();
        this.getPosition().addActionsFor(gameData, this, at2);
        roomActions.addAll(at2);
        if (at2.size() > 0) {
            at.add(roomActions);
        }

    }

    private void addWatchAction(ArrayList<Action> at) {
        TargetingAction watchAction = new WatchAction(this);
        if (watchAction.getNoOfTargets() > 0) {
            at.add(watchAction);
        }
    }

    private void addGiveAction(ArrayList<Action> at) {
        TargetingAction giveAction = new GiveAction(this);
        giveAction.addClientsItemsToAction(this);
        if (giveAction.getNoOfTargets() > 0 && giveAction.getWithWhats().size() > 0) {
            at.add(giveAction);
        }
    }

    private void addPutOnActions(ArrayList<Action> at) {
        PutOnAction act = new PutOnAction(this);
        if (act.getNoOfOptions() > 0) {
            at.add(act);
        }
    }

    private void addAttackActions(ArrayList<Action> at) {
        TargetingAction attackAction = new AttackAction(this);
        if (attackAction.getNoOfTargets() > 0) {
            attackAction.addClientsItemsToAction(this);
            at.add(attackAction);
        }
    }

    private void addBasicActions(ArrayList<Action> at) {
        if (!isDead()) {
            at.add(new SearchAction());
        }
    }

    public abstract double getMaxHealth();
	
	public abstract void moveIntoRoom(Room brig);

    public boolean isPassive() {
        return getCharacter().isPassive();
    }

    public GameCharacter getInnermostCharacter() {
        GameCharacter current = getCharacter();
        while (current instanceof CharacterDecorator) {
            current = ((CharacterDecorator) current).getInner();
        }
        return current;
    }

    public void giveStartingItemsToSelf() {
        List<GameItem> startingItems = this.getCharacter().getStartingItems();
        Logger.log("Giving starting items to " + this.getPublicName());
        for (GameItem it : startingItems) {
            if (it.canBePickedUp()) {
                this.addItem(it, null);
            } else {
                this.getPosition().addItem(it);
            }
        }
    }

    public List<Room> getVisibleMap(GameData gameData) {
        if (getCharacter() != null) {
            return getCharacter().getVisibleMap(gameData);
        }

        return gameData.getRooms();
    }
}
