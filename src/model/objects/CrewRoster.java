package model.objects;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;

public class CrewRoster extends GameObject {

	public CrewRoster() {
		super("Crew Roster");
	}
	
	@Override
	public void addSpecificActionsFor(Player cl, ArrayList<Action> at) {
		at.add(new Action("Inspect Roster", false) {
			
			@Override
			public void setArguments(List<String> args) { }
			
			@Override
			protected String getVerb() {
				return "inspected crew roster";
			};
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				List<Actor> acts = new ArrayList<>();
				acts.addAll(gameData.getPlayersAsList());
				acts.addAll(gameData.getNPCs());
				performingClient.addTolastTurnInfo("Crew Roster;");
				for (Actor a : acts) {
					if (a.getCharacter().isCrew()) {
						performingClient.addTolastTurnInfo(a.getPublicName());
					}
				}
			}
		});
	}

}
