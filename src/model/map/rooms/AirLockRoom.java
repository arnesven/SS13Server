package model.map.rooms;

import model.Actor;
import model.GameData;
import model.characters.decorators.InSpaceCharacterDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.NoPressureEvent;
import model.events.ambient.ColdEvent;
import model.items.suits.Equipment;
import model.items.suits.SpaceSuit;
import model.map.doors.AirLockDoor;
import model.map.doors.Door;
import model.map.floors.AirLockFloorSet;
import model.map.floors.FloorSet;
import model.objects.general.OxyMaskDispenser;
import model.objects.general.AirlockPanel;

public class AirLockRoom extends StationRoom {

	private boolean hasPressure;
	protected Event noPressureEvent = null;
	private ColdEvent coldEvent = null;

	public AirLockRoom(int ID, int number, int x, int y,
			int width, int height, int[] neighbors, Door[] doors) {
		super(ID, "Air Lock #"+number         ,
                x, y, width, height, neighbors, doors);
		this.hasPressure = true;
		this.addObject(new AirlockPanel(this));
        this.addObject(new OxyMaskDispenser(this));
	}

	@Override
    public FloorSet getFloorSet() {
		return new AirLockFloorSet();
	}

	@Override
	protected String getAppearanceScheme() {
		return "WallsNoWindows-Space";
	}

	private void cycle(GameData gameData, Actor performingClient) {
		for (Door d : getDoors()) {
			if (d instanceof AirLockDoor) {
				((AirLockDoor) d).cycle(gameData, performingClient);
			}
		}
	}

	public void pressurize(GameData gameData, Actor performingClient) {
		if (hasPressure) {
			return;
		}
		this.hasPressure = true;
		if (noPressureEvent != null) {
			removeEvent(noPressureEvent);
			gameData.removeEvent(noPressureEvent);
		}
		if (coldEvent != null) {
			removeEvent(coldEvent);
			gameData.removeEvent(coldEvent);
		}
		performingClient.addTolastTurnInfo("Pressurized " + getName());
		cycle(gameData, performingClient);

		for (Actor a : getActors()) {
			if (!a.isAI()) {
				if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof InSpaceCharacterDecorator)) {
					a.stopBeingInSpace();
				}
			}
		}
	}

	public boolean hasPressure() {
		return hasPressure;
	}

	public void depressurize(GameData gameData, Actor performingClient) {
		if (!hasPressure) {
			return;
		}
		hasPressure = false;
		noPressureEvent = new NoPressureEvent(this, performingClient, true);
		gameData.addEvent(noPressureEvent);
		addEvent(noPressureEvent);

		coldEvent = new ColdEvent(this);
		gameData.addEvent(coldEvent);
		addEvent(coldEvent);

		performingClient.addTolastTurnInfo("Depressurized " + getName());
		cycle(gameData, performingClient);
		for (Actor a : getActors()) {
			if (!a.isAI()) {
				if (!a.isInSpace()) {
					a.goToSpace(gameData);
				}
			}
		}
	}

	private boolean hasASpacesuitOn(Actor cl) {
		return cl.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) instanceof SpaceSuit;
	}


}
