package model;

import java.io.Serializable;
import java.util.*;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.actions.*;
import model.actions.fancyframeactions.ShowLootingFancyFrame;
import model.actions.general.*;
import model.characters.crew.CrewCharacter;
import model.characters.decorators.*;
import model.characters.general.*;
import model.items.EmergencyKit;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.items.suits.Wearable;
import model.items.weapons.Weapon;
import model.map.rooms.RelativePositions;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;
import util.Logger;

public abstract class Actor  implements ItemHolder, SpriteObject, Serializable {

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

    public boolean beAttackedBy(Actor performingClient, Weapon item, GameData gameData) {
        boolean succ = getCharacter().beAttackedBy(performingClient, item, gameData);
//        if (item.hasRealSound()) {
//            if (this instanceof Player) {
//                ((Player)this).getSoundQueue().add(item.getRealSound());
//            }
//
//            if (performingClient instanceof Player) {
//                ((Player)performingClient).getSoundQueue().add(item.getRealSound());
//            }
//        }
        return succ;
    }
	
	/**
	 * Sets the character for this Actor.
	 * This will set the character for the Actor.
	 * @param charr the new character
	 */
	public void setCharacter(GameCharacter charr) {
		this.character = charr;
        if (charr != null) {
            getCharacter().setActor(this);
        }
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

        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof InfectedCharacter);

	}

	public String getPublicName() {
		return getCharacter().getPublicName();
	}

    public String getPublicName(Actor whosAsking) {
        return getCharacter().getPublicName();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return getCharacter().getSprite(whosAsking);
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
	public void addYourselfToRoomInfo(ArrayList<String> info, Actor whosAsking) {
		if ( whosAsking == this) {
            info.add(getCharacter().getUnanimatedSprite(whosAsking).getName() + "<img>" + "You" + (getCharacter().isVisible()?"":" (invisible)") + "<img>{}");
        } else if (getCharacter().isVisible()){
            info.add(getCharacter().getUnanimatedSprite(whosAsking).getName() + "<img>" + whosAsking.getCharacter().getHowPerceived(this) + "<img>{}");
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

	public CharacterDecorator getDecorator(InstanceChecker check) {
        if (getCharacter() instanceof CharacterDecorator) {
            CharacterDecorator decor = (CharacterDecorator)getCharacter();
            if (check.checkInstanceOf(decor)) {
                return decor;
            } else {
                return decor.getDecorator(check);
            }

        }

        throw new NoSuchInstanceException("Could not remove instance!");

    }
	
	public void putOnSuit(Wearable selectedItem) {
	    if (selectedItem instanceof SuitItem) {
	        this.getCharacter().putOnEquipment((SuitItem) selectedItem);
        } else {
            selectedItem.beingPutOn(this);
        }
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

        ArrayList<Action> generals = new ArrayList<>();
        if (getsActions()) {
            addBasicActions(generals);

            addRoomActions(gameData, at);
            addMoveActions(gameData, at);
            addAttackActions(generals);
            addWatchAction(generals);
            addLootAction(generals, gameData);
            addHighFiveAction(generals, gameData);
            if (this.hasInventory()) {
                addManageItemActions(gameData, at);
            }

           addCharacterSpecificActions(gameData, at);

        }

        ActionGroup general = new ActionGroup("General");
        general.addAll(generals);
        if (general.getActions().size() > 0) {
            at.add(general);
        }
        //groupTargetingActions(at);
        at.add(0, new DoNothingAction());

        //Action.uniquefiyList(gameData, at, this);

        getCharacter().modifyActionList(gameData, at);

        return at;
    }


    private void addMoveActions(GameData gameData, ArrayList<Action> at) {
        Action move = new MoveAction(gameData, this);
        if (move.getOptions(gameData, this).getSuboptions().size() > 0) {
            at.add(move);
        }
    }

    private void addHighFiveAction(ArrayList<Action> generals, GameData gameData) {
        Action high5 = new HighFiveAction(this);
        if (high5.getOptions(gameData, this).numberOfSuboptions() > 0) {
            generals.add(high5);
        }
    }

    private void addLootAction(ArrayList<Action> generals, GameData gameData) {
        TargetingAction loot = new LootAction(this);
        if (loot.getOptions(gameData, this).numberOfSuboptions() > 0) {
            generals.add(loot);
        }
    }



    private void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        ArrayList<Action> acts = new ArrayList<>();
        getCharacter().addCharacterSpecificActions(gameData, acts);
        if (acts.size() > 0) {
            ActionGroup abilities = new ActionGroup("Abilities");
            abilities.addAll(acts);
            at.add(abilities);
        }
    }

    public boolean getsActions() {
        if (getCharacter() != null) {
            return getCharacter().getsActions();
        }
        return !isDead();
    }

    public boolean hasInventory() {
        if (getCharacter() == null) {
            return false;
        }
        return getCharacter().hasInventory();
    }

//    private void groupTargetingActions(ArrayList<Action> at) {
//        List<Action> targetingActions = new ArrayList<>();
//        Iterator<Action> it = at.iterator();
//        while (it.hasNext()) {
//            Action a = it.next();
//            if (a instanceof TargetingAction) {
//                targetingActions.add(a);
//                it.remove();
//            }
//        }
//        if (targetingActions.size() > 0) {
//            ActionGroup ag = new ActionGroup("Interaction");
//            ag.addAll(targetingActions);
//            at.add(ag);
//        }
//    }

    private void addManageItemActions(GameData gameData, ArrayList<Action> at2) {
        ArrayList<Action> at = new ArrayList<>();
        addGiveAction(at);
        addDropActions(gameData, at);


        addItemActions(gameData, at);

        if (at.size() > 0) {
            ActionGroup manageItems = new ActionGroup("Inventory");
            manageItems.addAll(at);
            at2.add(manageItems);
        }
    }



    private void addItemActions(GameData gameData, ArrayList<Action> at) {
        ArrayList<Action> itActions = new ArrayList<>();
        for (GameItem it : getItems()) {
            it.addYourActions(gameData, itActions, this);
        }

        itActions.sort(new Comparator<Action>() {
            @Override
            public int compare(Action o1, Action o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

//        for (GameItem it : getPosition().getItems()) {
//            if (it.isUsableFromFloor()) {
//                it.addYourActions(gameData, itActions, this);
//            }
//        }

        if (itActions.size() > 0) {
            ActionGroup ag = new ActionGroup("Use");
            ag.addAll(itActions);
            at.add(ag);
        }
    }

    private void addPickUpActions(GameData gameData, ArrayList<Action> at) {
        if (hasInventory()) {
            PickUpAction pickUpAction = new PickUpAction(this);
            if (pickUpAction.getOptions(gameData, this).numberOfSuboptions() > 0) {
                at.add(pickUpAction);
            }
        }
    }
//
//    private void addPickUpAndUseActions(GameData gameData, ArrayList<Action> at) {
//        PickupAndUseAction pickUpUse = new PickupAndUseAction(this, gameData);
//        if (pickUpUse.getOptions(gameData, this).numberOfSuboptions() > 0) {
//            at.add(pickUpUse);
//        }
//
//    }

    private void addDropActions(GameData gameData, ArrayList<Action> at) {
        DropAction dropAction = new DropAction(this);
        if (dropAction.getOptions(gameData, this).numberOfSuboptions() > 0) {
            at.add(dropAction);
        }
    }

    private void addRoomActions(GameData gameData, ArrayList<Action> at) {
        ActionGroup roomActions = new ActionGroup("Room");

        ArrayList<Action> at2 = new ArrayList<>();
        if (this.hasInventory()) {
            addPickUpActions(gameData, at2);
            //addPickUpAndUseActions(gameData, at2);
            addUnequipAction(gameData, at2);
            addPutOnActions(at2);
            addDragAction(gameData, at2);
            addFollowAction(gameData, at2);
        }
        this.getPosition().addActionsFor(gameData, this, at2);
        roomActions.addAll(at2);

        if (this instanceof Player) {
            for (Room r : getCharacter().getMiniMapRooms(gameData)) {
                ActionGroup roomGroup = new ActionGroup(r.getName());
                List<Action> acts = new ArrayList<>();
                acts.addAll(r.getActionData(gameData, (Player)this));
                if (acts.size() > 0) {
                    roomGroup.addAll(acts);
                    roomActions.add(roomGroup);
                }
            }
        }

        at.add(roomActions);
    }

    private void addUnequipAction(GameData gameData, ArrayList<Action> at2) {
        Action unequip = new UnequipAction();
        if (unequip.getOptions(gameData, this).numberOfSuboptions() > 0) {
            at2.add(unequip);
        }
    }

    private void addFollowAction(GameData gameData, ArrayList<Action> at2) {
        FollowAction follow = new FollowAction(this);
        if (follow.getNoOfTargets() > 0) {
            at2.add(follow);
        }
        StopFollowingAction unfollow = new StopFollowingAction(this);
        if (unfollow.getNoOfTargets() > 0) {
            at2.add(unfollow);
        }

    }

    private void addDragAction(GameData gameData, ArrayList<Action> at2) {
        if (!getCharacter().checkInstance((GameCharacter gc) -> gc instanceof DraggingDecorator)) {
            TargetingAction drag = new DragAction(this);
            if (drag.getNoOfTargets() > 0) {
                at2.add(drag);
            }
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
        if (isCrew()) {
            this.addItem(new EmergencyKit(), null);
        }
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

        return gameData.getNonHiddenStationRooms();
    }

    @Override
    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        List<Action> list = new ArrayList<>();
        if (r == forWhom.getPosition()) {
            if (forWhom.getsActions() && forWhom.getCharacter().getsTargetingActions()) {
                AttackAction atk = new AttackAction(forWhom);
                if (atk.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom))) {
                    atk.stripAllTargetsBut(this.getAsTarget());
                    atk.addClientsItemsToAction(forWhom);
                    list.add(atk);
                }
                List<TargetingAction> simpleTargetingActions = new ArrayList<>();
                simpleTargetingActions.add(new WatchAction(forWhom));
                simpleTargetingActions.add(new DragAction(forWhom));
                simpleTargetingActions.add(new FollowAction(forWhom));
                simpleTargetingActions.add(new StopFollowingAction(forWhom));
                simpleTargetingActions.add(new HighFiveAction(forWhom));
                if (forWhom.hasInventory()) {
                    simpleTargetingActions.add(new LootAction(forWhom));
                    simpleTargetingActions.add(new ShowLootingFancyFrame(forWhom, gameData, this));
                }
                for (TargetingAction ta : simpleTargetingActions) {
                    if (ta.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom))) {
                        ta.stripAllTargetsBut(this.getAsTarget());
                        list.add(ta);
                    }
                }

                GiveAction give = new GiveAction(forWhom);
                if (give.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom)) && forWhom.hasInventory()) {
                    give.stripAllTargetsBut(this.getAsTarget());
                    give.addClientsItemsToAction(forWhom);
                    list.add(give);
                }

//                ArrayList<Action> tmpList = new ArrayList<>();
//                forWhom.getCharacter().addCharacterSpecificActions(gameData, tmpList); // TODO: This is a REAL problem when there are many actors in the room!
//                for (Action a : tmpList) {
//                    if (a.getOptions(gameData, forWhom).numberOfSuboptions() > 0) {
//                        if (a.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom))) {
//                            if (a instanceof TargetingAction) {
//                                ((TargetingAction) a).stripAllTargetsBut(this.getAsTarget());
//                            }
//                            list.add(a);
//                        }
//                    }
//                }

                if (this == forWhom) {
                //    DropAction drop = new OverlayDropAction(forWhom);
                //    list.add(drop);
                //    forWhom.getCharacter().addCharacterSpecificActions(gameData, (ArrayList<Action>) list);
                }

                ArrayList<Action> extraList = new ArrayList<>();
                getCharacter().addActionsForActorsInRoom(gameData, forWhom, extraList);
                for (Action a : extraList) {
                    if (a instanceof TargetingAction) {
                        ((TargetingAction) a).stripAllTargetsBut(this.getAsTarget());
                    }
                    list.add(a);
                }
                //for (drop.getOptions(gameData, watchAction).)
            }
        }
        return list;

    }

    @Override
    public boolean hasAbsolutePosition() {
        if (getCharacter() == null) {
            return false;
        }
        return getCharacter().getSpacePosition() != null;
    }

    @Override
    public void setAbsolutePosition(double x, double y, double z) {
        getCharacter().getSpacePosition().setX(x);
        getCharacter().getSpacePosition().setY(y);
        getCharacter().getSpacePosition().setZ(z);
    }

    @Override
    public double getAbsoluteX() {
        if (getCharacter() == null || getCharacter().getSpacePosition() == null) {
            return 0.0;
        }
        return getCharacter().getSpacePosition().getX();
    }

    @Override
    public double getAbsoluteY() {
        if (getCharacter() == null || getCharacter().getSpacePosition() == null) {
            return 0.0;
        }
        return getCharacter().getSpacePosition().getY();
    }

    @Override
    public double getAbsoluteZ() {
        if (getCharacter() == null || getCharacter().getSpacePosition() == null) {
            return 0.0;
        }
        return getCharacter().getSpacePosition().getZ();
    }

    public Set<Room> findMoveToAblePositions(GameData gameData) {
        HashSet<Room> canMoveTo = new HashSet<>();
        canMoveTo.add(getPosition());
        int movement = getCharacter().getMovementSteps();
        for (int i = 1; i <= movement; ++i) {
            Set<Room> newLocations = new HashSet<>();
            for (Room r : canMoveTo) {
                newLocations.addAll(r.getNeighborList());
            }
            canMoveTo.addAll(newLocations);
        }
        canMoveTo.remove(getPosition());
        canMoveTo.addAll(getCharacter().getExtraMoveToLocations(gameData));
        return canMoveTo;
    }

    public boolean isAI() {
        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter);
    }

    public boolean isHuman() {
        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HumanCharacter);
    }


    public boolean isCrew() {
        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CrewCharacter);
    }

    public boolean isRobot() {
        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof RobotCharacter);
    }


    public boolean isAnimal() {
        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AnimalCharacter);
    }

    public boolean isInSpace() {
        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof InSpaceCharacterDecorator);
    }

    public void stopBeingInSpace() {
        removeInstance((GameCharacter gc) -> gc instanceof InSpaceCharacterDecorator);
        getCharacter().setSpacePosition(null);
    }


    public void goToSpace(GameData gameData) {
        setCharacter(new InSpaceCharacterDecorator(getCharacter(), gameData));
    }

    public boolean isIntelligentCreature() {
        return isHuman() || isRobot() || isAI() || getInnermostCharacter() instanceof ChimpCharacter;
    }

    public int numberOfSeen(Class<? extends GameObject> someClass) {
        int counter = 0;
        for (GameObject obj : getPosition().getObjects()) {
            if (obj.getClass() == someClass) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public RelativePositions getPreferredRelativePosition() {
        if (getCharacter() != null) {
            return getCharacter().getPreferredRelativePosition();
        }
        return null;
    }
}
