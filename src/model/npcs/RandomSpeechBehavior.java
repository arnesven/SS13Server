package model.npcs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.Action;
import model.actions.SensoryLevel;

public class RandomSpeechBehavior extends SpontaneousAct {

	public RandomSpeechBehavior(final String filename) {
		super(100.0, new Action("Talk", SensoryLevel.SPEECH) {
			
			private String talkString;
			
			@Override
			public String getDescription(Actor whosAsking) {
				return "TARS said \"" + talkString + "\"";
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
					String person = "";
					
					List<Actor> targets = performingClient.getPosition().getActors();
					
					do {
						//System.out.println("TARS targets: " + targets.toString());
						person = targets.get(MyRandom.nextInt(targets.size())).getBaseName();
						System.out.println("TARS wants to talkt to " + person);
					} while (person.equals("TARS"));
					
					talkString2 = talkString2.replace("$r", person);
				}
				return talkString2;
			}
		});
	}

}
