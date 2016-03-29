package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.items.weapons.BluntWeapon;
import model.actions.SensoryLevel;

public class Saxophone extends BluntWeapon {

	public Saxophone() {
		super("Saxophone", 1.5);
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		super.addYourActions(gameData, at, cl);
		at.add(new Action("Play Saxophone", SensoryLevel.PHYSICAL_ACTIVITY) {
			
			@Override
			protected String getVerb(Actor whosAsking) {
				return "Played a cool jazz tune";
			}
			
			@Override
			public void setArguments(List<String> args, Actor performingClient) { }
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				performingClient.addTolastTurnInfo("You play a cool jazz tune.");
			}
		});
	}
	
}
