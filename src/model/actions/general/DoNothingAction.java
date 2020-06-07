package model.actions.general;

import java.util.List;

import model.Actor;
import model.GameData;
import model.MostWantedCriminals;
import model.Player;
import model.items.NoSuchThingException;


public class DoNothingAction extends Action {

	public DoNothingAction() {
		super("Do Nothing", SensoryLevel.NO_SENSE);
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Did nothing";
	}

	@Override
	public void execute(GameData gameData, Actor performingClient) {
		if (performingClient instanceof Player) {
			Player player = (Player)performingClient;
			player.setActionPoints(player.getActionPoints() + 1);
			player.addTolastTurnInfo("You saved one Action Point (AP). It can be used to perform some actions " +
				"during a later turn. You have a total of " + player.getActionPoints() + " AP.");
		}
    }

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		
	}

}
