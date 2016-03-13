package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.map.Room;
import model.objects.KeyCardLock;
import model.actions.SensoryLevel;

public class LockRoomAction extends Action {

	private Room to;
	private Room from;
	private KeyCardLock lock;

	public LockRoomAction(Room to, Room from, KeyCardLock keyCardLock) {
		super("Lock " + to.getName(), SensoryLevel.OPERATE_DEVICE);
		this.to  = to;
		this.from = from;
		this.lock = keyCardLock;
	}

	@Override
	protected String getVerb() {
		return "locked the " + to.getName();
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		int[] newNArr = new int[from.getNeighbors().length-1];
		int x = 0;
		for (int i = 0; i < from.getNeighbors().length; ++i) {
			if (from.getNeighbors()[i] != to.getID()) {
				newNArr[x] = from.getNeighbors()[i];
				x++;
			}
		}
	
		from.setNeighbors(newNArr);
		performingClient.addTolastTurnInfo("You locked the " + to.getName() + ".");
		lock.setLocked(true);
	}

	@Override
	public void setArguments(List<String> args) { }

}