package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.actions.general.SensoryLevel;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.roomactions.CanAlsoMoveToForOneTurnDecorator;
import model.map.SpacePosition;
import model.map.rooms.AirLockRoom;
import model.map.rooms.Room;
import model.map.rooms.SpaceRoom;
import util.Logger;

public class AirlockPanel extends ElectricalMachinery {

	private final AirLockRoom airLock;

	public AirlockPanel(AirLockRoom roomRef) {
		super("Airlock Control", roomRef);
		this.airLock = roomRef;
		setPowerPriority(4);
	}

	public AirlockPanel(AirLockRoom airLock, Room otherRoom) {
		super("Airlock Control", otherRoom);
		this.airLock = airLock;
		setPowerPriority(4);
	}

	@Override
	public double getPowerConsumption() {
		return 0.0002; // 200 W
	}

	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(makeApplicableAction(gameData));
		if (airLock != getPosition() && cl.findMoveToAblePositions(gameData).contains(airLock)) {
			at.add(new MoveIntoAndCycleAction(makeApplicableAction(gameData)));
		}
		if (airLock != getPosition() && cl.isInSpace() && airLock.hasPressure()) {
			at.add(new MoveIntoAirlockAction());
		}

	}

	@Override
    public Sprite getSprite(Player whosAsking) {
		if (!isPowered()) {
			return new Sprite("pressurepanelnopower", "monitors.png", 132, this);
		}
        if (airLock.hasPressure()) {
			return new Sprite("pressurepanel", "monitors.png", 0, this);
		}
		return new Sprite("pressurepanelnopressure", "monitors.png", 124, this);
    }

    public Action makeApplicableAction(GameData gameData) {
		if (airLock.hasPressure()) {
			return makeDepressurizeAction(gameData);		
		} else {
			return makePressurizeAction(gameData);
		}
	}

	private Action makePressurizeAction(GameData gameData) {
		return new Action("Pressurize", SensoryLevel.OPERATE_DEVICE) {

			@Override
			public void setArguments(List<String> args, Actor p) { }

			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				airLock.pressurize(gameData, performingClient);
			}

			@Override
			protected String getVerb(Actor whosAsking) {
				return "Pressurized an airlock";
			}
		};
	}

	private Action makeDepressurizeAction(GameData gameData) {
		return new Action("Depressurize", SensoryLevel.OPERATE_DEVICE) {

			@Override
			public void setArguments(List<String> args, Actor p) { }

			@Override
			protected void execute(GameData gameData, final Actor performingClient) {
				airLock.depressurize(gameData, performingClient);
			}

			@Override
			protected String getVerb(Actor whosAsking) {
				return "Depressurized an airlock";
			}
		};
		
	}

	public boolean getPressure() {
		return airLock.hasPressure();
	}

	private class MoveIntoAndCycleAction extends Action {
		private final Action innerAction;

		public MoveIntoAndCycleAction(Action action) {
			super("Move Into and " + action.getName(), SensoryLevel.OPERATE_DEVICE);
			this.innerAction = action;
		}

		@Override
		protected String getVerb(Actor whosAsking) {
			return "moved into the airlock";
		}

		@Override
		protected void execute(GameData gameData, Actor performingClient) {
			performingClient.moveIntoRoom(airLock);
			performingClient.addTolastTurnInfo("You moved into the airlock.");
			innerAction.doTheAction(gameData, performingClient);
		}

		@Override
		protected void setArguments(List<String> args, Actor performingClient) {

		}
	}

	private class MoveIntoAirlockAction extends Action {

		public MoveIntoAirlockAction() {
			super("Move into Airlock", SensoryLevel.OPERATE_DEVICE);
		}

		@Override
		protected String getVerb(Actor whosAsking) {
			return "moved into the airlock";
		}

		@Override
		protected void execute(GameData gameData, Actor performingClient) {
			if (performingClient instanceof Player) {
				((Player) performingClient).setNextMove(AirlockPanel.this.airLock.getID());
				performingClient.setCharacter(new CanAlsoMoveToForOneTurnDecorator(performingClient.getCharacter(), AirlockPanel.this.airLock));
				gameData.executeAtEndOfRound(performingClient, this);
			}
		}

		@Override
		protected void setArguments(List<String> args, Actor performingClient) {

		}

		@Override
		public void lateExecution(GameData gameData, Actor performingClient) {
			super.lateExecution(gameData, performingClient);
			Logger.log(performingClient.getPublicName() + " no longer in space!");
			performingClient.getCharacter().setSpacePosition(new SpacePosition(AirlockPanel.this.airLock));
			performingClient.stopBeingInSpace();
		}
	}
}
