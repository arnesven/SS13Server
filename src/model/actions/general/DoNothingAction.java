package model.actions.general;

import java.util.List;

import model.Actor;
import model.GameData;
import model.MostWantedCriminals;
import model.Player;
import model.actions.QuickAction;
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
			QuickAction.saveActionPoint(gameData, player);


		}
    }

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		
	}

	@Override
	public boolean doesSetPlayerReady() {
		return false;
	}
}
