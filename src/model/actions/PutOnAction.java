package model.actions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.items.GameItem;
import model.items.suits.SuitItem;

public class PutOnAction extends Action {

	private List<SuitItem> options = new ArrayList<>();
	private SuitItem selectedItem;
	private Actor putOnner;
	private Actor lootVictim = null;

	public PutOnAction(Actor ap) {
		super("Put on", SensoryLevel.PHYSICAL_ACTIVITY);
		this.putOnner = ap;
		if (ap.getCharacter().getSuit() == null) {
			addOptions(ap);
		} else if (ap.getCharacter().getSuit().permitsOver()) {
			addOptions(ap);
		}
	}

	private void addOptions(Actor ap) {
		for (GameItem it : ap.getItems()) {
			if (it instanceof SuitItem) {
				options.add((SuitItem)it);
			}
		}
		for (GameItem it : ap.getPosition().getItems()) {
			if (it instanceof SuitItem) {
				options.add((SuitItem)it);
			}
		}
		
		for (Actor actor : ap.getPosition().getActors()) {
			if (actor.isDead() && actor.getCharacter().getSuit() != null) {
				options.add(actor.getCharacter().getSuit());
			}
		}
	}

	@Override
	protected String getVerb() {
		if (selectedItem == null) {
			return "failed";
		}
		return "put on the " + selectedItem.getName();
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (selectedItem == null) {
			performingClient.addTolastTurnInfo("Your action failed!");
			return;
		}
		
		if (performingClient.getItems().contains(selectedItem)) {
			performingClient.getItems().remove(selectedItem);
		} else if (performingClient.getPosition().getItems().contains(selectedItem)) {
			performingClient.getPosition().getItems().remove(selectedItem);
		} else if (lootVictim != null) {
			lootVictim.getCharacter().removeSuit();
		} else {
			performingClient.addTolastTurnInfo("The " + selectedItem.getName() + " is gone!");
			return;
		}
		
		performingClient.getCharacter().putOnSuit(selectedItem);
		selectedItem.beingPutOn(performingClient);
	
		performingClient.addTolastTurnInfo("You put on the " + selectedItem.getName() + ".");
		
	}

	@Override
	public void setArguments(List<String> args) {
		System.out.println("Setting arg for put on" + args.get(0));
		for (GameItem it : putOnner.getItems()) {
			if (it.getName().equals(args.get(0))) {
				selectedItem = (SuitItem)it;
				return;
			}
		}
		for (GameItem it : putOnner.getPosition().getItems()) {
			if (it.getName().equals(args.get(0))) {
				selectedItem = (SuitItem)it;
				return;
			}
		}
		for (Actor actor : putOnner.getPosition().getActors()) {
			if (actor.isDead()) {
				System.out.println("Dead guys suit: " + actor.getCharacter().getSuit());
				if (actor.getCharacter().getSuit().getName().equals(args.get(0))) {
					selectedItem = (SuitItem)(actor.getCharacter().getSuit());
					System.out.println("Looting a suit of a dead body " + selectedItem.getName());
					lootVictim  = actor;
					return;
				}
			}
		}
		
		
	}

	public int getNoOfOptions() {
		return options.size();
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (GameItem it : options) {
			buf.append(it.getName() + "{}");
		}
		return getName() + "{" + buf.toString() + "}";
	}
	
}