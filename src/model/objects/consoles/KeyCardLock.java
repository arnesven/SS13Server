package model.objects.consoles;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.LockRoomAction;
import model.actions.objectactions.UnlockRoomAction;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.GameMap;
import model.map.Room;

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
		return performingClient.getPosition() == to || performingClient.getPosition() == from;
	}
	
	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		if (hasKeyCard(cl)) {
			if (locked) {
				at.add(new UnlockRoomAction(to, from, this));
			} else {
				at.add(new LockRoomAction(to, from, this));
			}
		}
	}

	private boolean hasKeyCard(Player cl) {
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
		System.out.println(" room unlocked because of lock broke!");
        GameMap.joinRooms(to, from);
        setLocked(false);
    }
	
	@Override
	public void onPowerOff(GameData gameData) {
		for (Actor a : gameData.getActors()) {
			a.addTolastTurnInfo("AI; Attention, " + to.getName() + " unlocked because of power failure!");
		}
		System.out.println(" room unlocked because of power failure!");
		unlockRooms();
	}
	
}
