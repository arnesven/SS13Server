package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.actions.general.SensoryLevel;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.AirLockRoom;
import model.map.rooms.Room;
import model.map.rooms.SpaceRoom;

public class AirlockPanel extends ElectricalMachinery {

	private final AirLockRoom airLock;

	public AirlockPanel(AirLockRoom roomRef) {
		super("Airlock Control", roomRef);
		this.airLock = roomRef;
	}

	public AirlockPanel(AirLockRoom airLock, Room otherRoom) {
		super("Airlock Control", otherRoom);
		this.airLock = airLock;
	}

	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(makeApplicableAction(gameData));
		if (airLock != getPosition() && cl.findMoveToAblePositions(gameData).contains(airLock)) {
			at.add(new MoveIntoAndCycleAction(makeApplicableAction(gameData)));
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
}
