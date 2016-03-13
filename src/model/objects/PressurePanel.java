package model.objects;

import java.util.ArrayList;
import java.util.List;

import model.actions.SensoryLevel;
import model.Target;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.events.Event;
import model.events.NoPressureDamage;
import model.events.NoPressureEvent;
import model.map.AirLockRoom;

public class PressurePanel extends ElectricalMachinery {

	private AirLockRoom roomRef;
	private boolean hasPressure;
	protected Event noPressureEvent = null;

	public PressurePanel(AirLockRoom roomRef) {
		super("Pressure control");
		this.roomRef = roomRef;
		this.hasPressure = true;
	}

	@Override
	protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(makeApplicableAction(gameData));
		
	}

	public Action makeApplicableAction(GameData gameData) {
		if (hasPressure) {
			return makeDepressurizeAction(gameData);		
		} else {
			return makePressurizeAction(gameData);
		}
	}

	private Action makePressurizeAction(GameData gameData) {
		return new Action("Pressurize", SensoryLevel.OPERATE_DEVICE) {

			@Override
			public void setArguments(List<String> args) { }

			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				PressurePanel.this.hasPressure = true;
				if (noPressureEvent != null) {
					roomRef.removeEvent(noPressureEvent);
				}
				performingClient.addTolastTurnInfo("Pressurized " + roomRef.getName());
			}
		};
	}

	private Action makeDepressurizeAction(GameData gameData) {
		return new Action("Depressurize", SensoryLevel.OPERATE_DEVICE) {

			@Override
			public void setArguments(List<String> args) { }

			@Override
			protected void execute(GameData gameData, final Actor performingClient) {
				PressurePanel.this.hasPressure = false;
				Event e = new NoPressureEvent(PressurePanel.this, roomRef, performingClient);
				gameData.addEvent(e);
				roomRef.addEvent(e);
				PressurePanel.this.noPressureEvent = e;
				performingClient.addTolastTurnInfo("Depressurized " + roomRef.getName());
			}
		};
		
	}

	public boolean getPressure() {
		return hasPressure;
	}

}
