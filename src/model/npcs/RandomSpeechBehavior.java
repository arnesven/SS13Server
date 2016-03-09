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
				int lines = 0;
				try {
					for (Scanner scanner = new Scanner(new File(filename)) ; 
							scanner.hasNext(); scanner.nextLine() ) {
						lines++;
					}
					Scanner scanner = new Scanner(new File(filename));
					for (int i = 0; i < MyRandom.nextInt(lines) ; ++i) {
						talkString = scanner.nextLine();
					}
					System.out.println("TARS next line: " + talkString);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
	}

}
