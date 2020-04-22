package model;

import java.io.Serializable;
import java.util.*;

import graphics.ClientInfo;
import graphics.OverlaySprite;
import graphics.sprites.*;
import model.characters.general.AICharacter;
import model.characters.special.SpectatorCharacter;
import model.fancyframe.FancyFrame;
import model.fancyframe.SinglePageFancyFrame;
import model.items.suits.SuitItem;
import model.map.rooms.SpaceRoom;
import model.movepowers.MovePowerRoom;
import sounds.Sound;
import sounds.SoundQueue;
import model.actions.general.Action;
import model.actions.general.ActionGroup;
import model.characters.general.GameCharacter;
import model.characters.decorators.InfectedCharacter;
import model.events.damage.Damager;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.map.rooms.Room;
import util.HTMLText;
import util.Logger;


/**
 * @author erini02
 * Class for representing a client in the game. 
 * This means, a player, and the data pertaining to that player.
 */
public class Player extends Actor implements Target, Serializable {

	private boolean ready = false;
	private int nextMove = 0;
	private List<String> personalHistory = new ArrayList<>();
	private Action nextAction;
	private HashMap<String, Boolean> jobChoices = new HashMap<>();
    private PlayerSettings settings = new PlayerSettings();
    private SoundQueue soundQueue = new SoundQueue(this);
    private ClientInfo clientInf0 = new ClientInfo();
    private PhysicalBody styleBody = new PhysicalBody();
    private CharacterCreation charCreation = new CharacterCreation();
    private String selectedMovePower;
	private FancyFrame fancyFrame;
	private int dataState = 0;


	public Player(GameData gameData) {
		Sprite charPreview = styleBody.getSprite(); // DON'T REMOVE!
		fancyFrame = new FancyFrame(null);
		jobChoices.put("Artificial Intelligence", false);
	}


	/**
	 * Sets the ready flag for the client. True meaning that the client is ready to continue
	 * to the next phase of the game. False means that the client is not ready yet.
	 * @param ready, the new value for the ready flag.
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}


	/**
	 * Gets wether or not the client is ready to continue or not
	 * @return true if the client is ready, false otherwise
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * Returns wether or not the player is dead or not
	 * @return true if the client is dead
	 */
	public boolean isDead() {
		if (getCharacter() == null) {
			return false;
		}

		return getCharacter().getHealth() == 0.0;
	}

	/**
	 * Sets the character for this client.
	 * This will set the character for the client and also set the client reference
	 * in the character to this client.
	 * @param charr the new character
	 */
	@Override
	public void setCharacter(GameCharacter charr) {
		super.setCharacter(charr);

	}

	/**
	 * Gets the current health of the player.
	 * This is a wrapper to the character's health.
	 * @return the health of the character of this player.
	 * @throws IllegalStateException if the character has not yet been set.
	 */
	public double getCurrentHealth() {
		if (getCharacter() == null) {
			throw new IllegalStateException("Character has not yet been set for this client.");
		}
		return getCharacter().getHealth();
	}

	/**
	 * Gets the Last turn info for this client.
	 * The last turn info is a collection of strings
	 * which tells the player what happened during the
	 * last turn of the game.
	 * @return The information as a list of strings.
	 */
	public List<String> getLastTurnInfo() {
		List<String> lastTurnInfo = new ArrayList<>();
		lastTurnInfo.addAll(personalHistory);

		return lastTurnInfo ;
	}

	/**
	 * Gets the suit of the player as a string.
	 * @return the suit of the player
	 */
//	public String getSuit() {
//		if (this.getCharacter().getSuit() == null) {
//			return "*None*";
//		}
//		return this.getCharacter().getSuit().getFullName(this);
//	}

	/**
	 * Sets the ID of the next move which the player will make.
	 * The GUI will send an ID of the room which the player has selected.
     * @param id the ID representing the room which to the player will move next.
     */
	public void setNextMove(Integer id) {
        nextMove = id;
	}

	/**
	 * Gets the ID of the room which this player will move to next.
	 * @return the ID of the room which this player will move to next.
	 */
	public int getNextMove() {
		return this.nextMove;
	}

	/**
	 * Gets the info for the player's current room. 
	 * The room info is a collection of strings which
	 * tells the player what hidden characters/items/objects
	 * are in that same room with him/her.
	 * @return the information as a list of strings.
	 */
	public List<String> getRoomInfo(GameData gameData) {
		return this.getPosition().getInfo(gameData, this);
	}

	/**
	 * Gets this player's character's real name.
	 * I.e. the full name, including all modifiers, like (host) or anthing else
	 * which has been added. This is the name as it would appear to the player itself.
	 * @return
	 */
	public String getCharacterRealName() {
		return getCharacter().getFullName();
	}

	/**
	 * Get's the client's characters public name, i.e. as it appears to
	 * anyone else on the station.
	 * @return the public name of the character
	 */
	public String getCharacterPublicName() {
		return getCharacter().getPublicName();
	}

	/**
	 * Gets all locations which can be selected by the player
	 * as potential destinations to which he/she can move to.
	 * Currently, all clients can move two rooms at a time.
	 * @param gameData the Game's data
	 * @return the locations IDs as an array
     * @deprecated
	 */
	public int[] getSelectableLocations(GameData gameData) {
		int steps = getCharacter().getMovementSteps();

		List<Room> list = new ArrayList<>();
		// TODO: Work these back into actions.
        //getCharacter().getMovePowersIfPlayer(gameData, list);
        ArrayList<Integer> movablePlaces = new ArrayList<>();
//		for (Room r : list) {
//		    movablePlaces.add(r.getID());
//        }

		if (steps > 0) {
			int[] neigbors = this.getPosition().getNeighbors();

			for (int n : neigbors) {
				addNeighborsRecursively(gameData, n, movablePlaces, steps-1);
			}
		}
		movablePlaces.add(this.getPosition().getID());

		int[] result = new int[movablePlaces.size()];
		for (int i = 0; i < movablePlaces.size(); ++i) {
			result[i] = movablePlaces.get(i);
		}
		return result;
	}


	private void addNeighborsRecursively(GameData gameData, int n,
			ArrayList<Integer> movablePlaces, int steps) {
		if (! movablePlaces.contains(n) ) {
			movablePlaces.add(n);
		}
		if (steps > 0) {
            try {
                for (int n2 : gameData.getRoomForId(n).getNeighbors()) {
                    addNeighborsRecursively(gameData, n2, movablePlaces, steps-1);
                }
            } catch (NoSuchThingException e) {
                Logger.log(Logger.CRITICAL, "Did not find such a neighbor room");
            }
        }
	}

    /**
     * Moves the player into another room
     *
     * @param room the room to be moved into.
     */
    public void moveIntoRoom(Room room) {
        setNextMove(room.getID());
        if (!isDead()) {
            if (this.getPosition() != null && this.getPosition().getID() != this.getNextMove()) {
                if (this.getPosition() != null) {
                    try {
                        this.getPosition().removePlayer(this);
                    } catch (NoSuchThingException e) {
                        Logger.log(Logger.CRITICAL, "Tried removing player from room, but it wasn't there!");
                    }
                }
                Logger.log("Moving player " + this.getCharacterRealName() +
                        " into room " + room.getName());
                this.setPosition(room);
                room.addPlayer(this);
            } else if (this.getPosition() == null) {
                Logger.log("Moving player " + this.getCharacterRealName() +
                        " into room " + room.getName());
                this.setPosition(room);
                room.addPlayer(this);
            }
        }
    }



	/**
	 * Gets the string representation of this players
	 * current action tree. I.e. the actions which this player can
	 * take depending on what room, what items and what hidden players
	 * are in that same room with him/her.
	 * @param gameData the Game's data
	 * @return the string representing the selectable actions.
	 */
	public String getActionListString(GameData gameData) {
		return Action.makeActionListString(gameData, getActionList(gameData), this);
	}





	/**
	 * Parses a action object from the action string sent by the
	 * client gui.
	 * @param actionString the string describing which action was selected
	 * @param gameData the Game's data.
	 */
	public void parseActionFromString(String actionString, GameData gameData) {
		ArrayList<Action> at = getActionList(gameData);
		String actionStr = actionString.replaceFirst("root,", "");

		List<String> args = new ArrayList<>();
		args.addAll(Arrays.asList(actionStr.split(",")));
		//Logger.log("Action tree: " + at.toString());
		for (Action a : at) {
			if (a.getName().equals(args.get(0))) {
                while (a instanceof ActionGroup) {
                    for (Action a2 : ((ActionGroup)a).getActions()) {
					//	Logger.log("Parsing for " +  a2.getName() + ", strings is: " + args.toString());
						if (a2.getName().equals(args.get(1))) {
                            a = a2;
							args = args.subList(1, args.size());
							break;
						}
					}
                    Logger.log(" -> Next action!");
                }



                args = args.subList(1, args.size());
				a.setActionTreeArguments(args, this);
				this.nextAction = a;
				try {
					gameData.setPlayerReady(gameData.getClidForPlayer(this), true);
				} catch (NoSuchThingException e) {
					e.printStackTrace(); // should not happen
				}
				return;
			}
		}	
		Logger.log(Logger.CRITICAL, "Could not find action for this action string " + actionString + ".");

	}

    public void parseOverlayActionFromString(String actionStr, GameData gameData) {
        List<OverlaySprite> overlaySprites = getOverlayStrings(gameData);
        String actionString = actionStr.replaceFirst("root,", "");
        List<String> args = new ArrayList<>();
        args.addAll(Arrays.asList(actionString.split(",")));
		Action bestGuess = null;
        boolean actionFound = false;
        for (OverlaySprite sp : overlaySprites) {
            if (sp.getSprite().getObjectReference() != null) {
                SpriteObject obj = sp.getSprite().getObjectReference();
                for (Action a : obj.getOverlaySpriteActionList(gameData, sp.getRoom(), this)) {
                    if (a.getName().equals(args.get(0))) {
                    	if (args.size() == 1 || a.isAmongOptions(gameData, this, args.get(1))) {
							// TODO: How can we really be sure we found the right action? There could be several "attack" for instance.
							List<String> newArgs = args.subList(1, args.size());
//                        String last = newArgs.remove(newArgs.size()-1);
//                        newArgs.add(0, last);
							a.setOverlayArguments(newArgs, this);
							this.nextAction = a;
							actionFound = true;
							break;
						} else {
                    		bestGuess = a;
						}
                    }
                }
            }
        }
        if (!actionFound) {
        	if (bestGuess == null) {
				Logger.log(Logger.CRITICAL, "WARNING: Overlay action could not be parsed!");
			} else {
        		Logger.log(Logger.INTERESTING, "Using best guess for next action: " + bestGuess.getName());
				List<String> newArgs = args.subList(1, args.size());
				bestGuess.setOverlayArguments(newArgs,  this);
				this.nextAction = bestGuess;
			}
		}
		try {
        	if (nextAction.doesSetPlayerReady()) {
				gameData.setPlayerReady(gameData.getClidForPlayer(this), true);
			}
		} catch (NoSuchThingException e) {
			e.printStackTrace(); // should not happen
		}

	}

    public void parseInventoryActionFromString(String actionStr, GameData gameData) {
        List<GameItem> gis = new ArrayList<>();
        gis.addAll(getItems());
        gis.addAll(getCharacter().getEquipment().getSuitsAsList());

        String actionString = actionStr.replaceFirst("root,", "");
        List<String> args = new ArrayList<>();
        args.addAll(Arrays.asList(actionString.split(",")));
        Logger.log("Parsing inventory action:" + args.toString());
        for (GameItem gi : gis) {
           List<Action> acts = gi.getInventoryActions(gameData, this);
           if (gi instanceof SuitItem) {
           		if (getCharacter().getEquipment().getSuitsAsList().contains(gi)) {
           			acts.addAll(((SuitItem) gi).getEquippedActions(gameData, this));
				}
		   }
           for (Action a : acts) {
               if (a.getName().equals(args.get(0))) {
               	   Logger.log("Action found! " + a.getName());
                   List<String> newArgs = args.subList(1, args.size());
                   a.setInventoryArguments(newArgs, this);
                   this.nextAction = a;
                   break;
               }
            }
        }

        // COuld also be ability!
		ArrayList<Action> abilityActions = new ArrayList<>();
        getCharacter().addCharacterSpecificActions(gameData, abilityActions);
		for (Action a : abilityActions) {
			if (a.getName().equals(args.get(0))) {
				Logger.log("Ability action found! " + a.getName());
				List<String> newArgs = args.subList(1, args.size());
				a.setInventoryArguments(newArgs, this);
				this.nextAction = a;
				break;
			}
		}

		try {
        	if (nextAction.doesSetPlayerReady()) {
				gameData.setPlayerReady(gameData.getClidForPlayer(this), true);
			}
		} catch (NoSuchThingException e) {
			e.printStackTrace(); // should not happen
		}
    }


	/**
	 * Applies the action previously selected in the action string.
	 * This method finds the corresponding action in the action tree
	 * and executes it.
	 * @param gameData the Game's data.
	 */
	public void applyAction(GameData gameData) {
		if (!isDead()) {
			Logger.log(getName() + "'s action is " + nextAction.getName());
			this.nextAction.doTheAction(gameData, this);
		} else {
            this.nextAction.setDeadBeforeApplied();
        }
	}

	public void setNextAction(Action nextAction) {
		this.nextAction = nextAction;
	}


	/**
	 * Adds a string to the last turn info.
	 * @param string the string to be added to the info.
	 */
	public void addTolastTurnInfo(String string) {
		personalHistory.add(string);
	}

	/**
	 * Clears the last turn info of this client.
	 * The last turn info is usually cleared between turns.
	 */
	public void clearLastTurnInfo() {
		personalHistory.clear();
	}

	@Override
	public String getName() {
		return getPublicName();
	}


	@Override
	public boolean isTargetable() {
		return !isDead() && getCharacter().isVisible();
	}


    public Action getNextAction() {
		return nextAction;
	}

	public double getMaxHealth() {
		return getCharacter().getMaxHealth();
	}

	@Override
	public double getHealth() {
		return this.getCurrentHealth();
	}

	@Override
	public Target getAsTarget() {
		return this;
	}

	@Override
	public void action(GameData gameData) {
		this.applyAction(gameData);
	}

	@Override
	public boolean hasSpecificReaction(MedKit objectRef) {
		return getCharacter().hasSpecificReaction(objectRef);
	}

	public void parseJobChoices(String rest) {
		String withoutBraces = rest.substring(2, rest.length()-1);
		Logger.log(withoutBraces);
		String[] opts = withoutBraces.split(",");
		for (String str : opts) {
			String[] keyVal = str.split("=");
			jobChoices.put(keyVal[0], Boolean.parseBoolean(keyVal[1]));
		}
		Logger.log("JobChoices " + jobChoices.toString());

	}

	public boolean checkedJob(String string) {
		if (jobChoices.get(string) == null) {
			return true;
		}
		return jobChoices.get(string);
	}

	public void beInfected(Actor performingClient) {
		this.setCharacter(new InfectedCharacter(this.getCharacter(), performingClient));
		performingClient.addTolastTurnInfo("You were " + HTMLText.makeText("Green", "infected") + " by " + performingClient.getPublicName(this));
		setFancyFrame(new SinglePageFancyFrame(getFancyFrame(),"Important!", HTMLText.makeColoredBackground("Lime", HTMLText.makeCentered("<br/>You were just infected by " + performingClient.getPublicName() +
				"!</b><br/>You are now on the " + HTMLText.makeWikiLink("modes/host", "Host") + " team. <br/>Keep the humans from destroying the hive!"))));

	}

	public void prepForNewGame() {
		this.nextMove = 0;
		this.nextAction = null;
		this.personalHistory = new ArrayList<>();
        soundQueue.clear();
        soundQueue.add(Sound.NEW_GAME);
	}

	@Override
	public void beExposedTo(Actor performingClient, Damager damager, GameData gameData) {
		getCharacter().beExposedTo(performingClient, damager);
        if (damager.hasRealSound()) {
            getSoundQueue().add(damager.getRealSound());
        }
	}


	@Override
	public boolean isHealable() {
		return getCharacter().isHealable();
	}

	public List<String> getItemsAsFullNameList(GameData gameData) {
		List<String> strs = new ArrayList<>();
		for (GameItem gi : getItems()) {
			strs.add(gi.howDoYouAppearInGUI(gameData, this));
		}
		return strs;
	}

	public void addUniquelyTolastTurnInfo(String text) {
		for (String s : this.getLastTurnInfo()) {
			if (s.equals(text)) {
				return;
			}
		}
		this.addTolastTurnInfo(text);
	}

	@Override
	public boolean canBeInteractedBy(Actor performingClient) {
		return this.getPosition() == performingClient.getPosition();
		
	}




    public boolean isPassive() {
        if (getCharacter() != null) {
            return getCharacter().isPassive();
        }
        return isDead();
    }


    public List<OverlaySprite> getOverlayStrings(GameData gameData) {

	   if (isDead() ||
                (gameData.getGameMode().gameOver(gameData) &&
                gameData.getGameState() == GameState.PRE_GAME)) {
        	return new SeeAllVision().getOverlaySprites(this, gameData);
        } else if (getCharacter() != null) {
            return getCharacter().getOverlayStrings(this, gameData);
        }
        return new OnlyTheAlwaysSpritesVision().getOverlaySprites(this, gameData);

    }

    public PlayerSettings getSettings() {
        return settings;
    }

    public void setSettings(String settingsString) {
        if (!settingsString.equals("[]")) {
            settingsString = settingsString.substring(1, settingsString.length() - 1);
            String[] parts = settingsString.split(",");
            for (String str : parts) {
                String[] strs = str.split("=");
                settings.set(strs[0], Boolean.parseBoolean(strs[1]));
            }
        }
    }


    public SoundQueue getSoundQueue() {
        return soundQueue;
    }

    public String getWeightString() {
        if (getCharacter() != null && (getInnermostCharacter() instanceof SpectatorCharacter || getInnermostCharacter() instanceof AICharacter)) {
            return "";
        }

        return String.format("%1$.1f kg", getCharacter().getTotalWeight());
    }

    public void setClientDimension(int width, int height) {
        this.clientInf0.setDimension(width, height);
    }

    public ClientInfo getClientInfo() {
	    return clientInf0;
    }

    public boolean isASpectator() {
        return getCharacter().checkInstance((GameCharacter gc) -> gc instanceof SpectatorCharacter);
    }

    public void activateMovementPower(int id, GameData gameData, MovementData moveData) {
	    if (isDead()) {
	        return;
        }

        for (Room r : getCharacter().getVisibleMap(gameData)) {
            if (r instanceof MovePowerRoom) {
                if (r.getName().equals(selectedMovePower)) {
                        ((MovePowerRoom) r).getMovePower().activate(gameData, this, moveData);
                    break;
                }
            }
        }
    }


    public List<Room> getMiniMap(GameData gameData) {
	    List<Room> res = new ArrayList<>();
        try {
            if (getCharacter() == null) {
                res.addAll(gameData.getMap().getStationRooms());
            } else {
                res.addAll(gameData.getMap().getRoomsForLevel(gameData.getMap().getLevelForRoom(getPosition()).getName()));
            }
            res.removeIf((Room r) -> (r.isHidden() || r instanceof SpaceRoom));
            if (getCharacter() != null) {
				for (Room extra : getCharacter().getVisibleMap(gameData)) {
					if (!res.contains(extra)) {
						res.add(extra);
					}
				}
			}
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setNextMovePower(int selected, GameData gameData) {
        Logger.log("TRIGGERED A Move-Power BUTTON: " + selected);
        for (Room r : getCharacter().getVisibleMap(gameData)) {
            if (r instanceof MovePowerRoom) {
                if (r.getID() == selected) {
                    selectedMovePower = r.getName();
                    Logger.log("        => " + selectedMovePower);
                    ((MovePowerRoom)r).gotTriggered(gameData, this);
                    break;
                }
            }
        }
    }


	public List<String> getEquippedItems(GameData gameData) {
		if (getCharacter() == null) {
			return new ArrayList<>();
		} else {
			return getCharacter().getEquipment().getGUIData(gameData, this);
		}
	}


	public void setCharacterStyle() {
		getCharacter().setPhysicalBody(styleBody);
	}


	public PhysicalBody getStyleBody() {
		return styleBody;
	}

	public Object getStylePreviewName() {
		return styleBody.getSprite().getName();
	}

	public FancyFrame getFancyFrame() {
		return this.fancyFrame;
	}

	public void setFancyFrame(FancyFrame ff) {
		this.fancyFrame = ff;
	}

    public int getDataState() {
		return this.dataState;
    }

	public void refreshClientData() {
		this.dataState++;
	}

	public CharacterCreation getCharacterCreation() {
		return charCreation;
	}
}
