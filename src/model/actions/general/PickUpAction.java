package model.actions.general;

import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.items.general.GameItem;

public class PickUpAction extends Action {

	private Actor ap;
	private GameItem item;

	public PickUpAction(Actor clientActionPerformer) {
		super("Pick up", SensoryLevel.PHYSICAL_ACTIVITY);
		ap = clientActionPerformer;
	}

	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (performingClient.getPosition().getItems().contains(item)) {
			performingClient.addTolastTurnInfo("You picked up the " + item.getPublicName(performingClient) + ".");
			performingClient.getPosition().getItems().remove(item);
			performingClient.getCharacter().giveItem(item, null);
		} else {
			performingClient.addTolastTurnInfo("You failed to pick up the " + item.getPublicName(performingClient) + "!");
			
		}
		
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "picked up";
	}
	
	@Override
	public String getDescription(Actor whosAsking) {
		return super.getDescription(whosAsking) + " the " + item.getPublicName(whosAsking);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = new ActionOption(this.getName());
		for (GameItem gi : ap.getPosition().getItems()) {
			opt.addOption(gi.getPublicName(whosAsking) + String.format(" (%.1f kg)", gi.getWeight()));
		}
		return opt;
	}
	
	public void setItem(GameItem it) {
		this.item = it;
	}
	
	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		for (GameItem it : ap.getPosition().getItems()){
			if (args.get(0).contains(it.getPublicName(performer))) {
				setItem(it);
				return;
			}
		}
		throw new NoSuchElementException("No such item found for pick up action");
	}


}
