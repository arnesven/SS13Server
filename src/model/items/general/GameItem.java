package model.items.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.PickUpAction;
import model.characters.general.GameCharacter;
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

    private final boolean usableFromFloor;
    private String name;
	private double weight;
	private Room position;
	private GameCharacter holder = null;
    private Sprite sprite = new Sprite("keycard", "card.png", 1, this);
    private int cost;

    public GameItem(String string, double weight, boolean usableFromFloor, int cost) {
		this.name = string;
		this.weight = weight;
        this.usableFromFloor = usableFromFloor;
        this.cost = cost;
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

	public String howDoYouAppearInGUI(Player whosAsking) {
		return getSprite(whosAsking).getName() + "<img>" + getFullName(whosAsking) + "<img>" + getInventoryActionData();
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

    public boolean canBePickedUp() {
        return true;
    }

    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        PickUpAction pu = new PickUpAction(forWhom);
        List<Action> list = new ArrayList<>();
        if (pu.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom))) {
            list.add(pu);
        }
        return list;
    }

    private String getInventoryActionData() {
        return "{ServerStuff{}}";
    }
}
