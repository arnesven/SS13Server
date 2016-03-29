package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.map.GameMap;
import model.map.Room;
import model.objects.KeyCardLock;
import model.actions.SensoryLevel;
import model.items.KeyCard;

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
	protected String getVerb(Actor whosAsking) {
		return "locked the " + to.getName();
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (KeyCard.hasAnItem(performingClient, new KeyCard())) {
			GameMap.disconnect(to, from);
			performingClient.addTolastTurnInfo("You locked the " + to.getName() + ".");
			lock.setLocked(true);
		} else {
			performingClient.addTolastTurnInfo("What? The Key Card is gone! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) { }

}
