package model.actions;

import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.items.GameItem;

public class DropAction extends Action {

	private Actor ap;
	private GameItem item;

	public DropAction(Actor clientActionPerformer) {
		super("Drop", SensoryLevel.PHYSICAL_ACTIVITY);
		ap = clientActionPerformer;
		
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.addTolastTurnInfo("You dropped the " + item.getName() + ".");
		performingClient.getItems().remove(item);
		performingClient.getPosition().getItems().add(item);
	}


	@Override
	protected String getVerb() {
		return "Dropped";
	}
	
	@Override
	public String getDescription() {
		return super.getDescription() + " " + item.getName();
	}
	
	@Override
	public String toString() {
		String withWatString = "";
		for (GameItem gi : ap.getItems()) {
			withWatString += gi.getName() + "{}";
		}
		
		return getName() + "{" + withWatString + "}";
	}
	
	@Override
	public void setArguments(List<String> args) {
		for (GameItem it : ap.getItems()){
			if (args.get(0).equals(it.getName())) {
				this.item = it;
				return;
			}
		}
		throw new NoSuchElementException("No such item found for drop action");
	}




	
}
