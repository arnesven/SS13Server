package model.map.rooms;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.InSpaceCharacterDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.NoPressureEvent;
import model.events.ambient.ColdEvent;
import model.items.suits.Equipment;
import model.items.suits.SpaceSuit;
import model.map.DockingPoint;
import model.map.doors.AirLockDoor;
import model.map.doors.Door;
import model.map.floors.AirLockFloorSet;
import model.map.floors.FloorSet;
import model.objects.general.OxyMaskDispenser;
import model.objects.general.AirlockPanel;
import model.objects.power.LifeSupport;
import util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AirLockRoom extends StationRoom implements DockingPointRoom {

	private boolean hasPressure;
	protected Event noPressureEvent = null;
	private ColdEvent coldEvent = null;
	private List<DockingPoint> dockPoints;

	public AirLockRoom(int ID, int number, int x, int y,
			int width, int height, int[] neighbors, Door[] doors) {
		super(ID, "Airlock #"+number         ,
                x, y, width, height, neighbors, doors);
		this.hasPressure = true;
		this.addObject(new AirlockPanel(this));
        this.addObject(new OxyMaskDispenser(this));
        dockPoints = new ArrayList<>();
	}

	@Override
    public FloorSet getFloorSet() {
		return new AirLockFloorSet();
	}

	@Override
	protected String getPaintingStyle() {
		return "WallsNoWindows";
	}

	private void cycle(GameData gameData, Actor performingClient) {
		for (Door d : getDoors()) {
			if (d instanceof AirLockDoor) {
				((AirLockDoor) d).cycle(gameData);
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


	public void addDockingPoint(DockingPoint dockingPoint) {
		dockPoints.add(dockingPoint);
	}

	@Override
	public List<DockingPoint> getDockingPoints() {
		return dockPoints;
	}

    @Override
    public void setDocked(boolean b, GameData gameData, Set<Door> affectedDoors) {
        if (b == true) {
            for (Door d : getDoors()) {
                if (d instanceof AirLockDoor && affectedDoors.contains(d)) {
                    if (!((AirLockDoor)d).isFullyOpen()) {
                        ((AirLockDoor) d).cycle(gameData);
                    }
                }
            }
        } else {
            for (Door d : getDoors()) {
                if (d instanceof AirLockDoor && affectedDoors.contains(d)) {
                    if (((AirLockDoor)d).isFullyOpen()) {
                        ((AirLockDoor) d).cycle(gameData);
                    }
                }
            }
        }
    }

    public boolean hasAFreeExit() {
	    Logger.log("Checking for free exit");
	    for (DockingPoint dp : getDockingPoints()) {
	        Logger.log("Docking point " +dp.getName() + " vacant? " + dp.isVacant());
	        if (dp.isVacant()) {
	            return true;
            }
        }
        return false;
    }

	@Override
	public LifeSupport getLifeSupport() {
		if (hasPressure) {
			return super.getLifeSupport();
		} else {
			return null;
		}
	}

	@Override
	protected boolean getsTrashBin() {
		return false;
	}
}
