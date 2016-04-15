package model.npcs.behaviors;

import java.io.IOException;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;

public class RandomSpeechBehavior extends SpontaneousAct {

	public RandomSpeechBehavior(final String filename) {
		super(1.0, new Action("Talk", SensoryLevel.SPEECH) {
			
			private String talkString;
			
			@Override
			public String getDescription(Actor whosAsking) {
				return "TARS said \"" + talkString + "\"";
			}
			
			@Override
			protected String getVerb(Actor whosAsking) {
				return "talked";
			}
			
			@Override
			public void setArguments(List<String> args, Actor p) { }
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				if (performingClient.getPosition().getActors().size() > 1) {
					int lines = 0;
					try {
						talkString = MyRandom.getRandomLineFromFile(filename);
						System.out.println("Talkstring is " + talkString);
						talkString = replaceMarkers(gameData, performingClient, talkString);
						
						System.out.println("TARS next line: " + talkString);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}

			private String replaceMarkers(GameData gameData, Actor performingClient, String talkString2) {
				if (talkString2.contains("$r")) {
					Actor person = null;
					
					List<Actor> targets = performingClient.getPosition().getActors();
					
					do {
						//System.out.println("TARS targets: " + targets.toString());
						person = targets.get(MyRandom.nextInt(targets.size()));
						System.out.println("TARS wants to talkt to " + person.getBaseName());
					} while (person.equals("TARS"));
					
					talkString2 = talkString2.replace("$r", person.getBaseName());
					person.addTolastTurnInfo(performingClient.getPublicName() + " said \""+talkString2 + "\"");
				}
				
				return talkString2;
			}

			
		});
	}

}
