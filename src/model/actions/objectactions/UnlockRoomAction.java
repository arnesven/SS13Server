package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.Room;
import model.objects.consoles.KeyCardLock;

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
		if (GameItem.hasAnItem(performingClient, new KeyCard())) {
			lock.unlockRooms();
			performingClient.addTolastTurnInfo("You unlocked the " + to.getName() + ".");
			
		} else {
			performingClient.addTolastTurnInfo("What? The key card is gone! Your action failed.");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) {}

}
