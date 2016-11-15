package model.items.general;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.HideAction;

public abstract class HidableItem extends GameItem {

	public HidableItem(String string, double weight, int cost) {
		super(string, weight, cost);
	}

	private boolean hidden = false;

	public void setHidden(boolean b) {
		this.hidden = b;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	@Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		if (!hidden) {
			super.addYourselfToRoomInfo(info, whosAsking);
		}
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		if (!hidden) {
			at.add(new HideAction(this));
		}
	}

}
