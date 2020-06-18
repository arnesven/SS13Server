package model.map.rooms;


import model.Actor;
import model.GameData;
import model.Player;
import model.events.NukeSetByOperativesEvent;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.NukieFloorSet;
import model.objects.general.GameObject;
import model.objects.general.NuclearBomb;
import sounds.Sound;
import util.MyRandom;

public class NukieShipRoom extends Room {

	public NukieShipRoom(int i, int x, int y, int w, int h, int[] js, Door[] ds) {
		super(i, "Nuclear Ship", x, y, w, h, js, ds);
	}


	@Override
    public FloorSet getFloorSet() {
		return new NukieFloorSet();
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public boolean isPartOfStation() {
		return false;
	}

	public void activateNuke(GameData gameData, Player performingClient) {
		performingClient.getItems().remove(hasTheDisk(performingClient));
		NuclearBomb nuke = null;
		for (GameObject ob : getObjects()) {
			if (ob instanceof NuclearBomb) {
				nuke = (NuclearBomb) ob;
				gameData.addEvent(new NukeSetByOperativesEvent(nuke));
				break;
			}
		}
		performingClient.addTolastTurnInfo("You activated the nuke and sent it to SS13.");
		getObjects().remove(nuke);
		MyRandom.sample(gameData.getNonHiddenStationRooms()).addObject(nuke);
	}

	public static NuclearDisc hasTheDisk(Actor performingClient) {
		for (GameItem it : performingClient.getItems()) {
			if (it instanceof NuclearDisc) {
				return (NuclearDisc) it;
			}
		}
		return null;
	}

	@Override
	protected Sound getSpecificAmbientSound() {
		return new Sound("shipambience");
	}

	@Override
	public boolean startsWithPressure() {
		return false;
	}
}
