package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.items.GameItem;
import model.items.PDA;
import model.modes.TraitorGameMode;
import model.actions.SensoryLevel;

public class UsePDAAction extends Action {

	private TraitorGameMode traitorMode;
	private PDA pda;
	boolean order = true;
	private GameItem orderedItem;

	public UsePDAAction(TraitorGameMode traitorMode, PDA pda) {
		super("Use PDA", SensoryLevel.OPERATE_DEVICE);
		this.traitorMode = traitorMode;
		this.pda = pda;
	}
	
	@Override
	protected String getVerb() {
		if (orderedItem != null) {
			return "ordered " + orderedItem.getName() + " with a PDA";
		}
		return "used a PDA";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (!order) {
			if (traitorMode.getTraitors().size() > 1) {
				String others = "The other traitors are; ";
				for (Player p : traitorMode.getTraitors()) {
					if (performingClient != p) {
						others += p.getBaseName() + " (obj; \"" + traitorMode.getObjectives().get(p) + "\")";
					}
				}
				performingClient.addTolastTurnInfo(others);
			} else {
				performingClient.addTolastTurnInfo("You are the only traitor.");
			}
		} else {
			performingClient.addItem(orderedItem);
			pda.decrementUses();
			performingClient.addTolastTurnInfo(orderedItem.getName() + 
					" appeared! You put it in your inventory.");
		}

	}
	
	@Override
	public String toString() {
		String result = "Use PDA{Request Info{}";
		if (pda.getUsesLeft() > 0) {
			result += "Order Item (" + pda.getUsesLeft() +" left){";
			for (GameItem it : PDA.getOrderableItems()) {
				result += it.getName() + "{}";
			}
			
			result += "}";
		} 
		return result + "}";
	}
	

	@Override
	public void setArguments(List<String> args) {
		if (args.get(0).equals("Request Info")) {
			order = false;
		} else {
			for (GameItem it : PDA.getOrderableItems()) {
				if (it.getName().equals(args.get(1))) {
					orderedItem = it;
				}
			}
			order = true;
		}
	}

}
