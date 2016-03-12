package model.npcs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.Target;

public class RandomSpeechBehavior extends SpontaneousAct {

	public RandomSpeechBehavior(final String filename) {
		super(100.0, new Action("Talk", SensoryLevel.SPEECH) {
			
			private String talkString;
			
			@Override
			public String getDescription() {
				return "TARS said \"" + talkString + "\"";
			}
			
			@Override
			public void setArguments(List<String> args) { }
			
			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				if (performingClient.getPosition().getActors().size() > 1) {
					int lines = 0;
					try {
						for (Scanner scanner = new Scanner(new File(filename)) ; 
								scanner.hasNext(); scanner.nextLine() ) {
							lines++;
						}
						System.out.println("TARS-file hade " + lines + " lines");
						Scanner scanner = new Scanner(new File(filename));
						for (int i = 0; i < MyRandom.nextInt(lines)+1 ; ++i) {
							talkString = scanner.nextLine();
						}
						System.out.println("Talkstring is " + talkString);
						talkString = replaceMarkers(gameData, performingClient, talkString);
						scanner.close();
						System.out.println("TARS next line: " + talkString);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}

			private String replaceMarkers(GameData gameData, Actor performingClient, String talkString2) {
				if (talkString.contains("$r")) {
					String person = "";
					
					do {
						List<Target> targets = performingClient.getPosition().getTargets();
						//System.out.println("TARS targets: " + targets.toString());
						person = targets.get(MyRandom.nextInt(targets.size())).getName();
						System.out.println("TARS wants to talkt to " + person);
					} while (person.equals("TARS"));
					
					talkString = talkString.replace("$r", person);
				}
				return talkString;
			}
		});
	}

}
