package model.objects;

import java.util.ArrayList;

import model.Client;
import model.actions.Action;
import model.actions.UnlockRoomAction;
import model.items.GameItem;
import model.items.KeyCard;
import model.map.Room;

public class KeyCardLock extends GameObject {

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
	public void addSpecificActionsFor(Client cl, ArrayList<Action> at) {
		if (asKeyCard(cl)) {
			if (locked) {
				at.add(new UnlockRoomAction(to, from));
			} else {
				//TODO Add this action so you can lock again
				//at.add(new LockRoomAction(to, from));
			}
		}
	}

	private boolean asKeyCard(Client cl) {
		for (GameItem it : cl.getItems()) {
			if (it instanceof KeyCard) {
				return true;
			}
		}
		return false;
	}

}