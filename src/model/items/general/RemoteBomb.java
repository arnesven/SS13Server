package model.items.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DropAction;
import model.actions.general.SensoryLevel;

public class RemoteBomb extends BombItem {

	private boolean remoteGotten = false;
	
	public RemoteBomb() {
		super("Remote Bomb", 750);
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
		super.addYourActions(gameData, at, cl);
		if (!remoteGotten) {
			at.add(new SeparateDetonatorAction());
			at.add(new SeparateDetonatorAndDropAction());
		}
		
	}

	@Override
	public RemoteBomb clone() {
		return new RemoteBomb();
	}

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return super.getDescription(gameData, performingClient) +
				(isDemolitionsExpert(performingClient)?" This kind can be detonated remotely, just don't forget to separate the detonator from the bomb first.":"");
	}

	private class SeparateDetonatorAction extends Action {

		public SeparateDetonatorAction() {
			super("Separate Detonator", SensoryLevel.OPERATE_DEVICE);
		}

		@Override
		protected String getVerb(Actor whosAsking) {
			return getOperationString();
		}

		@Override
		public void setArguments(List<String> args, Actor p) { }


		@Override
		protected void execute(GameData gameData, Actor performingClient) {
			if (hasAnItemOfClass(performingClient, RemoteBomb.class)) {
			remoteGotten = true;
			GameItem det = new BombDetonator(RemoteBomb.this);
			performingClient.addItem(det, null);
			performingClient.addTolastTurnInfo("You separated the detonator from the bomb.");
			RemoteBomb.this.setWeight(RemoteBomb.this.getWeight() - det.getWeight());
			} else {
				performingClient.addTolastTurnInfo("What? the bomb was gone! Your action failed.");
			}
		}
	}

	private class SeparateDetonatorAndDropAction extends SeparateDetonatorAction {
		private DropAction dropAction;
		public SeparateDetonatorAndDropAction() {
			super();
			this.setName("Separate Detonator and Drop");
		}

		@Override
		public void setArguments(List<String> args, Actor p) {
			super.setArguments(args, p);
			dropAction = new DropAction(p);
			dropAction.setArguments(args, p);
		}

		@Override
		protected void execute(GameData gameData, Actor performingClient) {
			super.execute(gameData, performingClient);
			dropAction.doTheAction(gameData, performingClient);
		}
	}
}
