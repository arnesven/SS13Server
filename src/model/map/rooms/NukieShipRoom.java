package model.map.rooms;


import model.map.floors.FloorSet;
import model.map.floors.NukieFloorSet;

public class NukieShipRoom extends Room {

	public NukieShipRoom(int i, int x, int y, int w, int h, int[] js, double[] ds) {
		super(i, "Nuclear Ship", x, y, w, h, js, ds);
	}


	@Override
	protected FloorSet getFloorSet() {
		return new NukieFloorSet();
	}

	@Override
	public boolean isHidden() {
		return true;
	}
}
