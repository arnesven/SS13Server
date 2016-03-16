package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.map.Room;
import model.objects.KeyCardLock;

public class UnlockRoomAction extends Action {

	private Room to;
	private Room from;
	private KeyCardLock lock;

	public UnlockRoomAction(Room to, Room from, KeyCardLock keyCardLock) {
		super("Unlock " + to.getName(), SensoryLevel.OPERATE_DEVICE);
		this.to = to;
		this.from = from;
		this.lock = keyCardLock;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "unlocked the " + to.getName();
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
	public void setArguments(List<String> args, Actor p) {}

}
