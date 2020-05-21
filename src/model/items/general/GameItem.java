package model.items.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.*;
import model.actions.itemactions.ManageInventoryAction;
import model.actions.itemactions.ShowExamineFancyFrameAction;
import model.characters.decorators.HoldingItemDecorator;
import model.characters.general.GameCharacter;
import model.items.HandheldItem;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import util.Logger;

/**
 * @author erini02
 * Class representing an Item in the game.
 * An item is an object which can be held, dropped, picked up or used
 * by a character.
 */
public abstract class GameItem implements Locatable, SpriteObject, Serializable {

    private static int uid_counter = 1;
    private final boolean usableFromFloor;
    private String name;
    private final int uid;
	private double weight;
	private Room position;
	private GameCharacter holder = null;
    private Sprite sprite = new Sprite("somegameitem", "items_righthand2.png", 0, this);
    private int cost;

    public GameItem(String string, double weight, boolean usableFromFloor, int cost) {
		this.name = string;
		this.weight = weight;
        this.usableFromFloor = usableFromFloor;
        this.cost = cost;
        uid = uid_counter++;
	}

    public GameItem(String string, double weight, int cost) {
        this(string, weight, false, cost);
    }

	public abstract GameItem clone();

	/**
	 * Gets the name of this item as it appears to a person
	 * who knows everything about it (for instance someone owning it).
	 * @param whosAsking, can be null, check before using!
	 * @return
	 */
	public String getFullName(Actor whosAsking) {
		return name;
	}


	public String getUniqueName(Actor whosAsking) {
	    return getFullName(whosAsking) + " #" + uid;
    }

	/**
	 * Gets the name of this item as it appears from a distance.
	 * @param whosAsking, can be null! check before using!
	 * @return
	 */
	public String getPublicName(Actor whosAsking) {
		return name;
	}

	/**
	 * Gets the true name of this item, as it was instanciated.
	 * @return
	 */
	public String getBaseName() {
		return name;
	}



	@Override
	public final String toString() {
		return super.toString();
	}

    public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
        info.add(getSprite(whosAsking).getName() + "<img>" + getPublicName(whosAsking));
    }

	public String howDoYouAppearInGUI(GameData gameData, Player whosAsking) {
		return getSprite(whosAsking).getName() + "<img>" + getFullName(whosAsking) + "<img>" + getInventoryActionData(gameData, whosAsking);
	}




//	protected char getIcon() {
//		return 'i';
//	}

	/**
	 * Adds the items actions to the players list of actions.
	 * Overload this method for specific items so they add their
	 * actions.
     * @param at
     * @param cl
     */
	public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
	    if (cl instanceof Player) {
            at.add(new ShowExamineFancyFrameAction(gameData, (Player)cl, this));
        }
	}

	public double getWeight() {
		return weight;
	}

	protected void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Room getPosition() {
		if (holder != null) {
			return holder.getPosition();
		}
		return position;
	}

	public void setPosition(Room p) {
		position = p;
	}

	public void setHolder(GameCharacter gameCharacter) {
		this.holder = gameCharacter;

	}

    public static <E extends Class> boolean hasAnItemOfClass(Actor act, E className) {
        for (GameItem it : act.getItems()) {
            if (className.isInstance(it)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
	public static <E> boolean hasAnItem(Actor act, E obj) {
        return hasAnItemOfClass(act, obj.getClass());
	}

	public static <E> boolean containsItem(List<GameItem> startingItems, E obj) {
		for (GameItem it : startingItems) {
			if (it.getClass() == obj.getClass()) {
				return true;
			}
		}
		return false;
	}

	public static Locatable getItem(Actor victim, GameItem item) throws NoSuchThingException {
		for (GameItem it : victim.getItems()) {
			if (it.getClass() == item.getClass()) {
				return it;
			}
		}
		throw new NoSuchThingException("Did not find a " + item.getBaseName());
	}

    public static <E extends GameItem> E getItemFromActor(Actor victim, E obj) throws NoSuchThingException {
        for (GameItem it : victim.getItems()) {
            if (it.getClass() == obj.getClass()) {
                return (E)it;
            }
        }
        throw new NoSuchThingException("Did not find a " + obj.getBaseName());
    }


    // TODO: make abstract..?
    public Sprite getSprite(Actor whosAsking) {
        return sprite;
    }

    public void gotGivenTo(Actor to, Target from) {
        // is called when items are given
    }

    public GameItem getTrueItem() {
        return this;
    }

    @Deprecated
    public boolean isUsableFromFloor() {
        return usableFromFloor;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public ActionOption getActionOptions(Actor whosAsking) {
        return new ActionOption(getPublicName(whosAsking));
    }

    public void transferBetweenActors(Actor from, Actor to, String giveArgs) {
        to.getCharacter().giveItem(this, from.getAsTarget());
        from.getItems().remove(this);
    }


    public GameCharacter getHolder() {
        return holder;
    }

    /**
     *
     * @param cameFrom, can be null, careful!
     */
    public void gotAddedToRoom(Actor cameFrom, Room to) {
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int c) { cost = c;}

    public boolean canBePickedUp() {
        return true;
    }

    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        PickUpAction pu = new PickUpAction(forWhom);
        List<Action> list = new ArrayList<>();
        if (forWhom.hasInventory()) {
            if (pu.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom))) {
                list.add(pu);
            }

            PickupAndUseAction pua = new PickupAndUseAction(forWhom, gameData);
            if (pua.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom))) {
                list.add(pua);
            }

            ManageInventoryAction mia = new ManageInventoryAction("Pick Up/Drop", gameData, true, this, forWhom);
            list.add(mia);
        }

        addSpecificOverlayActions(gameData, r, forWhom, list);

        list.add(new ShowExamineFancyFrameAction(gameData, forWhom, this));


        return list;
    }

    protected void addSpecificOverlayActions(GameData gameData, Room r, Player forWhom, List<Action> list) {

    }

    private String getInventoryActionData(GameData gameData, Player forWhom) {
        String res =  Action.makeActionListStringSpecOptions(gameData, getInventoryActions(gameData, forWhom), forWhom);
        //Logger.log(res);
        return res;
    }

    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        ArrayList<Action> acts = new ArrayList<>();
        if (forWhom.getsActions()) {
            addYourActions(gameData, acts, forWhom);
            DropAction drop = new DropAction(forWhom);
            if (drop.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom)) ||
                    drop.isAmongOptions(gameData, forWhom, this.getFullName(forWhom))) {
                acts.add(drop);
            }

            if (forWhom.hasInventory()) {
                ManageInventoryAction mia = new ManageInventoryAction("Drop/Pick Up", gameData, false, this, forWhom);
                acts.add(mia);
            }
        }

        return acts;
    }

    public void makeHoldInHand(Actor performingClient) {
        if (this instanceof HandheldItem) {
            while (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HoldingItemDecorator)) {
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof HoldingItemDecorator);
            }
            performingClient.setCharacter(new HoldingItemDecorator(performingClient.getCharacter(), (HandheldItem) this));
        } else {
            throw new IllegalStateException(getBaseName() + " can't be held in hand!");
        }
    }

    @Override
    public boolean hasAbsolutePosition() {
        return false;
    }

    @Override
    public void setAbsolutePosition(double x, double y, double z) {
        //TODO
    }

    @Override
    public double getAbsoluteX() {
        return 0;
    }

    @Override
    public double getAbsoluteY() {
        return 0;
    }

    @Override
    public double getAbsoluteZ() {
        return 0;
    }

    public String getDescription(GameData gameData, Player performingClient) {
        return "<i>Description Needed!</i>";
    }

    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return "";
    }

    public boolean isRecyclable() {
        return true;
    }
}
