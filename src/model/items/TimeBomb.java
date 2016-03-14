package model.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.events.Event;


public class TimeBomb extends BombItem {


	private boolean timeSet;

	public TimeBomb() {
		super("Time Bomb", 2.0);
		this.timeSet = false;
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		if (!timeSet) {
		
			at.add(new Action("Set Time Bomb", 
					SensoryLevel.OPERATE_DEVICE) {

				private int selectedTime;

				@Override
				protected String getVerb() {
					return "fiddled with bomb";
				}

				@Override
				public String toString() {
					StringBuffer buf = new StringBuffer("Set Time Bomb{");
					for (int i = 0; i < 16; i++) {
						buf.append(i + " Rounds{}");
					}
					buf.append("}");
					return buf.toString();
				}

				@Override
				public void setArguments(List<String> args) {
					Scanner scan = new Scanner(args.get(0));
					selectedTime = scan.nextInt();
					scan.close();
				}

				@Override
				protected void execute(GameData gameData, final Actor performingClient) {
					timeSet = true;
					performingClient.addTolastTurnInfo("You set the time on the bomb");
					Event e = new Event() {

						private int timeLeft = selectedTime;

						@Override
						public double getProbability() {
							return 0;
						}

						@Override
						public void apply(GameData gameData) {
							System.out.println("Time left on bomb " + timeLeft);
							if (timeLeft == 0) {
								explode(gameData, performingClient);

							} 
							timeLeft--;

						}

						@Override
						public String howYouAppear(Actor performingClient) {
							return "";
						}

						@Override
						public SensoryLevel getSense() {
							return SensoryLevel.NO_SENSE;
						}

						@Override
						public boolean shouldBeRemoved(GameData gameData) {
							return timeLeft < 0;
						}

					};
					gameData.addEvent(e);
				}

			});
		}

	}


}
