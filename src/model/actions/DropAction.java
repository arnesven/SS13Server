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
		performingClient.addTolastTurnInfo("You dropped the " + item.getPublicName(performingClient) + ".");
	}


	@Override
	protected String getVerb(Actor whosAsking) {
		return "Dropped";
	}
	
	@Override
	public String getDescription(Actor whosAsking) {
		return super.getDescription(whosAsking) + " " + item.getPublicName(whosAsking);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (GameItem gi : ap.getItems()) {
			opt.addOption(gi.getFullName(whosAsking));
		}
		
		if (ap.getCharacter().getSuit() != null) {
			opt.addOption(ap.getCharacter().getSuit().getPublicName(whosAsking));
		}
		return opt;
	}
	
	
	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		if (ap.getCharacter().getSuit() != null) {
			if (args.get(0).equals(ap.getCharacter().getSuit().getPublicName(performingClient))) {
				this.item = ap.getCharacter().getSuit();
				return;
			}
		}
		for (GameItem it : ap.getItems()){
			if (args.get(0).equals(it.getFullName(performingClient))) {
				this.item = it;
				return;
			}
		}
		throw new NoSuchElementException("No such item found for drop action");
	}




	
}
