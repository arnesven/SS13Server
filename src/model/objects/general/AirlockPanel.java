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
import model.actions.objectactions.AirlockOverrideAction;
import model.actions.objectactions.MoveToOtherAirlocks;
import model.characters.decorators.InSpaceCharacterDecorator;
import model.characters.decorators.SpaceProtection;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.characters.general.OperativeCharacter;
import model.characters.general.RobotCharacter;
import model.events.Event;
import model.events.NoPressureEvent;
import model.events.ambient.ColdEvent;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.items.suits.Equipment;
import model.items.suits.SpaceSuit;
import model.map.doors.AirLockDoor;
import model.map.doors.Door;
import model.map.rooms.AirLockRoom;
import model.map.rooms.Room;

public class AirlockPanel extends ElectricalMachinery {

	private boolean hasPressure;
	protected Event noPressureEvent = null;
	private ColdEvent coldEvent = null;

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
	}

	private boolean hasASpacesuitOn(Actor cl) {
		return cl.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) instanceof SpaceSuit;
	}

	@Override
    public Sprite getSprite(Player whosAsking) {
		if (!isPowered()) {
			return new Sprite("pressurepanelnopower", "monitors.png", 132, this);
		}
        if (hasPressure) {
			return new Sprite("pressurepanel", "monitors.png", 0, this);
		}
		return new Sprite("pressurepanelnopressure", "monitors.png", 124, this);
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
					gameData.removeEvent(noPressureEvent);
				}
				if (coldEvent != null) {
					AirlockPanel.this.getPosition().removeEvent(coldEvent);
					gameData.removeEvent(coldEvent);
				}
				performingClient.addTolastTurnInfo("Pressurized " + AirlockPanel.this.getPosition().getName());
				if (AirlockPanel.this.getPosition() instanceof AirLockRoom) {
					((AirLockRoom)AirlockPanel.this.getPosition()).cycle(gameData, performingClient);
				}
				if (!performingClient.isAI() && performingClient.getPosition() == AirlockPanel.this.getPosition()) {
					if (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof InSpaceCharacterDecorator)) {
						performingClient.removeInstance((GameCharacter gc) -> gc instanceof InSpaceCharacterDecorator);
					}
				}
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
				AirlockPanel.this.noPressureEvent = new NoPressureEvent(AirlockPanel.this, AirlockPanel.this.getPosition(), performingClient, true);
				gameData.addEvent(noPressureEvent);
				AirlockPanel.this.getPosition().addEvent(noPressureEvent);

				AirlockPanel.this.coldEvent = new ColdEvent(AirlockPanel.this.getPosition());
				gameData.addEvent(coldEvent);
				AirlockPanel.this.getPosition().addEvent(coldEvent);

				performingClient.addTolastTurnInfo("Depressurized " + AirlockPanel.this.getPosition().getName());
				if (AirlockPanel.this.getPosition() instanceof AirLockRoom) {
					((AirLockRoom)AirlockPanel.this.getPosition()).cycle(gameData, performingClient);
				}
				if (!performingClient.isAI() && performingClient.getPosition() == AirlockPanel.this.getPosition()) {
					performingClient.setCharacter(new InSpaceCharacterDecorator(performingClient.getCharacter(), gameData));
				}
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
