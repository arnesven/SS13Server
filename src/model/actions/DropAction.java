package model.actions;

import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.items.GameItem;
import model.items.suits.SuitItem;

public class DropAction extends Action {

	private Actor ap;
	private GameItem item;

	public DropAction(Actor clientActionPerformer) {
		super("Drop", SensoryLevel.PHYSICAL_ACTIVITY);
		ap = clientActionPerformer;
		
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		SuitItem suit = performingClient.getCharacter().getSuit();
		if (item == suit) {
			suit.beingTakenOff(performingClient);
			performingClient.getCharacter().removeSuit();
		} else {
			performingClient.getItems().remove(item);
		}
		performingClient.getPosition().getItems().add(item);
		performingClient.addTolastTurnInfo("You dropped the " + item.getName() + ".");
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
		if (ap.getCharacter().getSuit() != null) {
			withWatString += ap.getCharacter().getSuit() + "{}";
		}
		
		return getName() + "{" + withWatString + "}";
	}
	
	@Override
	public void setArguments(List<String> args) {
		if (ap.getCharacter().getSuit() != null) {
			if (args.get(0).equals(ap.getCharacter().getSuit().getName())) {
				this.item = ap.getCharacter().getSuit();
				return;
			}
		}
		for (GameItem it : ap.getItems()){
			if (args.get(0).equals(it.getName())) {
				this.item = it;
				return;
			}
		}
		throw new NoSuchElementException("No such item found for drop action");
	}




	
}
