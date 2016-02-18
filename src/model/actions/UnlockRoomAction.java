package model.actions;

import java.util.List;

import model.GameData;
import model.map.Room;

public class UnlockRoomAction extends Action {

	private Room to;
	private Room from;

	public UnlockRoomAction(Room to, Room from) {
		super("Unlock " + to.getName(), true);
		this.to = to;
		this.from = from;
	}

	@Override
	protected void execute(GameData gameData, ActionPerformer performingClient) {
		int[] newNArr = new int[from.getNeighbors().length+1];
		for (int i = 0; i < from.getNeighbors().length; ++i) {
			newNArr[i] = from.getNeighbors()[i];
		}
		newNArr[from.getNeighbors().length] = to.getID();
		from.setNeighbors(newNArr);
		performingClient.addTolastTurnInfo("You unlocked the " + to.getName() + ".");
	}

	@Override
	public void setArguments(List<String> args) {
		// TODO Auto-generated method stub

	}

}
