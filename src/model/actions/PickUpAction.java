package model.actions;

import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.items.GameItem;

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
			performingClient.addTolastTurnInfo("You picked up the " + item.getName() + ".");
			performingClient.getPosition().getItems().remove(item);
			performingClient.getItems().add(item);
		} else {
			performingClient.addTolastTurnInfo("You failed to pick up the " + item.getName() + "!");
			
		}
		
	}

	@Override
	protected String getVerb() {
		return "picked up";
	}
	
	@Override
	public String getDescription() {
		return super.getDescription() + " the " + item.getName();
	}
	
	@Override
	public String toString() {
		String withWatString = "";
		for (GameItem gi : ap.getPosition().getItems()) {
			withWatString += gi.getName() + "{}";
		}
		
		return getName() + "{" + withWatString + "}";
	}
	
	@Override
	public void setArguments(List<String> args) {
		for (GameItem it : ap.getPosition().getItems()){
			if (args.get(0).equals(it.getName())) {
				this.item = it;
				return;
			}
		}
		throw new NoSuchElementException("No such item found for pick up action");
	}


}
