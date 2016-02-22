package model.actions;

import java.util.List;
import java.util.NoSuchElementException;

import model.GameData;
import model.items.GameItem;

public class PickUpAction extends Action {

	private ActionPerformer ap;
	private GameItem item;

	public PickUpAction(ActionPerformer clientActionPerformer) {
		super("Pick up", false);
		ap = clientActionPerformer;
	}

	
	@Override
	protected void execute(GameData gameData, ActionPerformer performingClient) {
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
	protected String getPrintString(ActionPerformer performingClient) {
		return super.getPrintString(performingClient) + " the " + item.getName();
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
