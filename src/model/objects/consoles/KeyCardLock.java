package model.objects.consoles;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.LockRoomAction;
import model.actions.objectactions.UnlockRoomAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.GameMap;
import model.map.Room;
import util.Logger;

public class KeyCardLock extends Console {

	private Room to;
	private Room from;
	boolean locked;

	public KeyCardLock(Room to, Room from, boolean isLocked, double hp) {
		super(to.getName() + " Lock", from);
		this.to = to;
		this.from = from;
		locked = isLocked;
		this.setMaxHealth(hp);
		this.setHealth(hp);
		this.setPowerPriority(1);
	}
	
	@Override
	public boolean canBeInteractedBy(Actor performingClient) {
		return performingClient.getPosition() == to || performingClient.getPosition() == from ||
                performingClient.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof AICharacter);
	}
	
	@Override
	public void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		if (hasKeyCard(cl)) {
			if (locked) {
				at.add(new UnlockRoomAction(to, from, this));
			} else {
				at.add(new LockRoomAction(to, from, this));
			}
		}
	}

	private boolean hasKeyCard(Actor cl) {
        if (cl.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof AICharacter)) {
            return true;
        }

		for (GameItem it : cl.getItems()) {
			if (it instanceof KeyCard) {
				return true;
			}
		}
		return false;
	}



	public void setLocked(boolean b) {
		this.locked = b;
	}


	public void lockRooms() {
		if (isBroken()) {
			return;
		}
		GameMap.separateRooms(to, from);
		setLocked(true);
	}


	public void unlockRooms() {
		if (isBroken()) {
			return;
		}
		GameMap.joinRooms(to, from);
		setLocked(false);
	}
	
	@Override
	public void thisJustBroke() {
		Logger.log(Logger.INTERESTING, " room unlocked because of lock broke!");
        GameMap.joinRooms(to, from);
        setLocked(false);
    }
	
	@Override
	public void onPowerOff(GameData gameData) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("AI; Attention, " + to.getName() + " unlocked because of power failure!", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        Logger.log(Logger.INTERESTING, " room unlocked because of power failure!");
		unlockRooms();
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("keycardlock", "computer_complaints.png", 1, 0);
    }

    @Override
    public boolean canBeDismantled() {
        return false;
    }
}
