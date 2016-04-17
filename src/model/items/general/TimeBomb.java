package model.items.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.Event;


public class TimeBomb extends BombItem {


	private boolean timeSet;

	public TimeBomb() {
		super("Time Bomb");
		this.timeSet = false;
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		super.addYourActions(gameData, at, cl);
		if (!timeSet) {
		
			at.add(new Action("Set Time Bomb", 
					SensoryLevel.OPERATE_DEVICE) {

				private int selectedTime;

				@Override
				protected String getVerb(Actor whosAsking) {
					return getOperationString();
				}

				@Override
				public ActionOption getOptions(GameData gameData, Actor whosAsking) {
					ActionOption buf = new ActionOption("Set Time Bomb");
					for (int i = 0; i < 16; i++) {
						buf.addOption(i + " Rounds");
					}
					return buf;
				}

				@Override
				public void setArguments(List<String> args, Actor p) {
					Scanner scan = new Scanner(args.get(0));
					selectedTime = scan.nextInt();
					scan.close();
				}

				@Override
				protected void execute(GameData gameData, final Actor performingClient) {
					if (!hasAnItem(performingClient, new TimeBomb())) {
						performingClient.addTolastTurnInfo("What? The bomb is gone! Your action failed.");
						return;
					}
					
					timeSet = true;
					performingClient.addTolastTurnInfo("You set the time on the bomb");
					Event e = new Event() {

						private int timeLeft = selectedTime;


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

	@Override
	public TimeBomb clone() {
		return new TimeBomb();
	}


}
