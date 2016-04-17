package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.items.general.GameItem;
import model.items.general.Locator;
import model.items.general.PDA;
import model.modes.TraitorGameMode;
import model.modes.TraitorObjective;
import model.actions.general.SensoryLevel;

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
	protected String getVerb(Actor whosAsking) {
		if (orderedItem != null) {
			return "ordered " + orderedItem.getPublicName(whosAsking) + " with a PDA";
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
						others += p.getBaseName() + " (obj; \"" + traitorMode.getObjectives().get(p).getText() + "\")";
					}
				}
				performingClient.addTolastTurnInfo(others);
			} else {
				performingClient.addTolastTurnInfo("You are the only traitor.");
			}
		} else {
			GameItem gi = orderedItem.clone();
			performingClient.addItem(gi, null);
			pda.decrementUses();
			performingClient.addTolastTurnInfo(orderedItem.getPublicName(performingClient) + 
					" appeared! You put it in your inventory.");
			
			if (gi instanceof Locator) {
				setTargetFromObjective(gameData, performingClient, gi);
				performingClient.addTolastTurnInfo("The locator is set to your objective target.");
			}
		}

	}
	
	private void setTargetFromObjective(GameData gameData,
			Actor performingClient, GameItem gi) {
		TraitorObjective obj = traitorMode.getObjectives().get(performingClient);
		if (obj != null) {
			((Locator)gi).setTarget(obj.getLocatable());
		}
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = new ActionOption("Use PDA");
		opt.addOption("Request Info");
		if (pda.getUsesLeft() > 0) {
			ActionOption order = new ActionOption("Order Item (" + pda.getUsesLeft() + " left)");
			for (GameItem it : PDA.getOrderableItems()) {
				order.addOption(it.getBaseName());
			}
			opt.addOption(order);
		} 
		
		return opt;
	}
	

	@Override
	public void setArguments(List<String> args, Actor p) {
		if (args.get(0).equals("Request Info")) {
			order = false;
		} else {
			for (GameItem it : PDA.getOrderableItems()) {
				if (it.getBaseName().equals(args.get(1))) {
					orderedItem = it;
				}
			}
			order = true;
		}
	}

}
