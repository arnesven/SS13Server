package model.objects;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.UnlockRoomAction;
import model.items.GameItem;
import model.items.KeyCard;
import model.map.Room;

public class KeyCardLock extends ElectricalMachinery {

	private Room to;
	private Room from;
	boolean locked;

	public KeyCardLock(Room to, Room from, boolean isLocked) {
		super(to.getName() + " Lock");
		this.to = to;
		this.from = from;
		locked = isLocked;
	}
	
	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		if (hasKeyCard(cl) && !isBroken()) {
			if (locked) {
				at.add(new UnlockRoomAction(to, from, this));
			} else {
				//TODO Add this action so you can lock again
				//at.add(new LockRoomAction(to, from));
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
}
