package model.items.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.Room;

/**
 * @author erini02
 * Class representing an Item in the game.
 * An item is an object which can be held, dropped, picked up or used
 * by a character.
 */
public abstract class GameItem implements Locatable, Serializable {

    private final boolean usableFromFloor;
    private String name;
	private double weight;
	private Room position;
	private GameCharacter holder = null;
    private Sprite sprite = new Sprite("keycard", "card.png", 1);

    public GameItem(String string, double weight, boolean usableFromFloor) {
		this.name = string;
		this.weight = weight;
        this.usableFromFloor = usableFromFloor;
	}

    public GameItem(String string, double weight) {
        this(string, weight, false);
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
		return getSprite(whosAsking).getName() + "<img>" + getFullName(whosAsking);
	}


	/**
	 * This is from the GUI-project:"a" - "actor.png"
	 * a - actor.png
	 * i - item.png
	 * o - object.png
	 * f - fire.png
	 * e -  event.png
	 * r - radioactive.png
	 * c - cold.png
	 * ? - unknown.png
	 * p - parasite.png
	 * P - parasite_dead.png
	 * A - alien.png
	 * ( - snake.png
	 * C - checmicals.png
	 * n - nuclear_disc.png
	 * l - locker.png
	 * m - med_storage.png
	 * u - fuel_storage.png
	 * \@ - computer.png
	 * B - bioscanner.png
	 * [ - applepie.png
	 * F - food.png
	 * w - weapon.png
	 * L - laserpistol.png
	 * g - shotgun.png
	 * x - fireext.png
	 * b - banana.png
	 * O - cookomatic.png
	 * k - knife.png
	 * ] - chimp.png
	 * I - aicore.png
	 * ) - pressurepanel.png
	 * d - device.png
	 * T - tars.png
	 * N - nuke.png
	 * M - medkit.png
	 * # - crate.png
	 * + - bible.png
	 * { - flamer.png
	 * ! - stunbaton.png
	 * y - keycard.png
	 * & - cat.png
	 * H - hive.png
	 * h - husk.png
	 * % - naked_man.png
	 * 8 - naked_woman.png
	 * U - suit.png
	 * } - grenade.png
	 * 2 - gloves.png
	 * X - saxophone.png
	 * q - firesuit.png
	 * Q - radsuit.png
	 * S - spacesuit.png
	 * 0 - opspacesuit.png
	 * z - chefshat.png
	 * Z - sunglasses.png
	 * @return the character representing what icon should be displayed to the user.
	 */
//	protected char getIcon() {
//		return 'i';
//	}

	/**
	 * Adds the items actions to the players list of actions.
	 * Overload this method for specific items so they add their
	 * actions.
	 * @param at
	 */
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
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
	
	public static <E> boolean hasAnItem(Actor act, E obj) {
		for (GameItem it : act.getItems()) {
			if (it.getClass() == obj.getClass()) {
				return true;
			}
		}
		return false;
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


    // TODO: make abstract..?
    public Sprite getSprite(Actor whosAsking) {
        return sprite;
    }

    public void gotGivenTo(GameCharacter to, Target from) {
        // is called when items are given
    }

    public GameItem getTrueItem() {
        return this;
    }

    public boolean isUsableFromFloor() {
        return usableFromFloor;
    }
}
