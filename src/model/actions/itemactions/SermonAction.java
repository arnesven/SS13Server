package model.actions.itemactions;

import java.io.FileNotFoundException;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.Bible;
import model.items.general.GameItem;

public class SermonAction extends Action {

	private Bible bible;

	public SermonAction(Bible bible) {
		super("Sing Sermon", SensoryLevel.NO_SENSE);
		this.bible = bible;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "sang a sermon";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (GameItem.hasAnItem(performingClient, new Bible())) {
		String sermon;
		try {
			sermon = MyRandom.getRandomLineFromFile("resources/BIBLE.TXT");
			performingClient.addTolastTurnInfo("You say \"" + sermon +"\".");
			
			for (Actor a : performingClient.getPosition().getActors()) {
				if (a != performingClient) {
					a.addTolastTurnInfo(performingClient.getPublicName() + 
							" said \"" + sermon + "\".");
				}
				bible.addGodPoints(1);
			}
		} catch (FileNotFoundException e) {
			
		}
		} else {
			performingClient.addTolastTurnInfo("What? The Holy Bible is gone! Your action failed!");
		}
		
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		
	}

}
