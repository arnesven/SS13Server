package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.items.HidableItem;
import model.actions.SensoryLevel;

public class HideAction extends Action {

	private HidableItem hidableItem;

	public HideAction(HidableItem hidableItem) {
		super("Hide", SensoryLevel.PHYSICAL_ACTIVITY);
		this.hidableItem = hidableItem;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "hid a " + hidableItem.getPublicName(whosAsking);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		opt.addOption(hidableItem.getPublicName(whosAsking));
		return opt;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		hidableItem.setHidden(true);
		performingClient.addTolastTurnInfo("You hid the " + hidableItem.getPublicName(performingClient));
		performingClient.getCharacter().getItems().remove(hidableItem);
		performingClient.getPosition().addItem(hidableItem);
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		// TODO Auto-generated method stub

	}

}