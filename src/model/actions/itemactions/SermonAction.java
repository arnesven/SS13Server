package model.actions.itemactions;

import java.io.FileNotFoundException;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;

public class SermonAction extends Action {

	public SermonAction() {
		super("Sing Sermon", SensoryLevel.NO_SENSE);
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "sang a sermon";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		String sermon = null;
		try {
			sermon = MyRandom.getRandomLineFromFile("resources/BIBLE.TXT");
			performingClient.addTolastTurnInfo("You say \"" + sermon +"\".");
			
			for (Actor a : performingClient.getPosition().getActors()) {
				if (a != performingClient) {
					a.addTolastTurnInfo(performingClient.getPublicName() + 
							" said \"" + sermon + "\".");
				}
			}
		} catch (FileNotFoundException e) {
			
		}
		
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		
	}

}
