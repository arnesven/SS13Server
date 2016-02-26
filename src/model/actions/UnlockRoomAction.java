package model.actions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.map.Room;
import model.objects.KeyCardLock;

public class UnlockRoomAction extends Action {

	private Room to;
	private Room from;
	private KeyCardLock lock;

	public UnlockRoomAction(Room to, Room from, KeyCardLock keyCardLock) {
		super("Unlock " + to.getName(), true);
		this.to = to;
		this.from = from;
		this.lock = keyCardLock;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		int[] newNArr = new int[from.getNeighbors().length+1];
		for (int i = 0; i < from.getNeighbors().length; ++i) {
			newNArr[i] = from.getNeighbors()[i];
		}
		newNArr[from.getNeighbors().length] = to.getID();
		from.setNeighbors(newNArr);
		performingClient.addTolastTurnInfo("You unlocked the " + to.getName() + ".");
		lock.setLocked(false);
	}

	@Override
	public void setArguments(List<String> args) {
		// TODO Auto-generated method stub

	}

}
