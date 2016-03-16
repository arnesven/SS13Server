package model.actions;

import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.objects.GameObject;
import model.objects.HiveObject;


public class SearchAction extends Action {

	public SearchAction() {
		super("Search", SensoryLevel.PHYSICAL_ACTIVITY);
	}


	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		boolean foundSomething = false;
		for (GameObject o : performingClient.getPosition().getObjects()) {
			if (o instanceof HiveObject) {
				if (!performingClient.isInfected()) {
					((HiveObject)o).setFound(true);
					performingClient.addTolastTurnInfo("You found the hive! All humans will now also see it while standing in this room.");
					foundSomething = true;
				} else {
					performingClient.addTolastTurnInfo("The hive is here, but why tell anybody?");
					foundSomething = true;
				}
			}
		}
		
		if (!foundSomething) {
			performingClient.addTolastTurnInfo("Nothing of interest here...");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor p) { // Not needed	
	}

}
