package model.items.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.DropAction;
import model.actions.general.SensoryLevel;
import model.events.Event;
import util.Logger;


public class TimeBomb extends BombItem {


	private boolean timeSet;

	public TimeBomb() {
		super("Time Bomb", 850);
		this.timeSet = false;
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
		super.addYourActions(gameData, at, cl);
		if (!timeSet) {
			at.add(new SetTimeAction());
			at.add(new SetTimeAndDropAction());
		}

	}

	@Override
	public TimeBomb clone() {
		return new TimeBomb();
	}


	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return super.getDescription(gameData, performingClient) +
				(isDemolitionsExpert(performingClient)?" This kind can be set to detonate after a certain number of rounds.":"");
	}

	private class SetTimeAction extends Action {

		private int selectedTime;

		public SetTimeAction() {
			super("Set Time Bomb", SensoryLevel.OPERATE_DEVICE);
		}

		@Override
		protected String getVerb(Actor whosAsking) {
			return getOperationString();
		}

		@Override
		public ActionOption getOptions(GameData gameData, Actor whosAsking) {
			ActionOption buf = super.getOptions(gameData, whosAsking);
			for (int i = 0; i < gameData.getNoOfRounds()-gameData.getRound(); i++) {
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
					Logger.log(Logger.INTERESTING, "Time left on bomb " + timeLeft);
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
	}

	public class SetTimeAndDropAction extends SetTimeAction {
		private DropAction dropAction;
		public SetTimeAndDropAction() {
			super();
			this.setName("Set Time and Drop");
		}

		@Override
		public void setArguments(List<String> args, Actor p) {
			super.setArguments(args, p);
			dropAction = new DropAction(p);
			dropAction.setArguments(args.subList(1, 2), p);
		}

		@Override
		protected void execute(GameData gameData, Actor performingClient) {
			super.execute(gameData, performingClient);
			dropAction.doTheAction(gameData, performingClient);
		}
	}
}
