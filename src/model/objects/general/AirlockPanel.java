package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.actions.characteractions.EscapeAndSetNukeAction;
import model.actions.general.SensoryLevel;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.MoveToOtherAirlocks;
import model.characters.decorators.SpaceProtection;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.characters.general.OperativeCharacter;
import model.characters.general.RobotCharacter;
import model.events.Event;
import model.events.NoPressureEvent;
import model.map.rooms.Room;

public class AirlockPanel extends ElectricalMachinery {

	private boolean hasPressure;
	protected Event noPressureEvent = null;

	public AirlockPanel(Room roomRef) {
		super("Airlock", roomRef);
		this.hasPressure = true;
	}

	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(makeApplicableAction(gameData));
		if (cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof SpaceProtection) ||
                cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HorrorCharacter) ||
                cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof RobotCharacter)) {
            at.add(new MoveToOtherAirlocks(this.getPosition(), gameData));
        }
        if (cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OperativeCharacter)) {
			at.add(new EscapeAndSetNukeAction());
		}
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("pressurepanel", "monitors.png", 0, this);
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
				AirlockPanel.this.hasPressure = true;
				if (noPressureEvent != null) {
					AirlockPanel.this.getPosition().removeEvent(noPressureEvent);
				}
				performingClient.addTolastTurnInfo("Pressurized " + AirlockPanel.this.getPosition().getName());
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
				AirlockPanel.this.hasPressure = false;
				Event e = new NoPressureEvent(AirlockPanel.this, AirlockPanel.this.getPosition(), performingClient, true);
				gameData.addEvent(e);
				AirlockPanel.this.getPosition().addEvent(e);
				AirlockPanel.this.noPressureEvent = e;
				performingClient.addTolastTurnInfo("Depressurized " + AirlockPanel.this.getPosition().getName());
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
