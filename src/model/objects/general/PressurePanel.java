package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.actions.general.SensoryLevel;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.events.Event;
import model.events.NoPressureEvent;
import model.map.AirLockRoom;

public class PressurePanel extends ElectricalMachinery {

	private AirLockRoom roomRef;
	private boolean hasPressure;
	protected Event noPressureEvent = null;

	public PressurePanel(AirLockRoom roomRef) {
		super("Pressure control", roomRef);
		this.roomRef = roomRef;
		this.hasPressure = true;
	}

	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(makeApplicableAction(gameData));
		
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("pressurepanel", "monitors.png", 0);
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
			public void setArguments(List<String> args, Actor p) { }

			@Override
			protected void execute(GameData gameData, Actor performingClient) {
				PressurePanel.this.hasPressure = true;
				if (noPressureEvent != null) {
					roomRef.removeEvent(noPressureEvent);
				}
				performingClient.addTolastTurnInfo("Pressurized " + roomRef.getName());
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
				PressurePanel.this.hasPressure = false;
				Event e = new NoPressureEvent(PressurePanel.this, roomRef, performingClient, true);
				gameData.addEvent(e);
				roomRef.addEvent(e);
				PressurePanel.this.noPressureEvent = e;
				performingClient.addTolastTurnInfo("Depressurized " + roomRef.getName());
			}

			@Override
			protected String getVerb(Actor whosAsking) {
				return "Depressurized an airlock";
			}
		};
		
	}

	public boolean getPressure() {
		return hasPressure;
	}

}
