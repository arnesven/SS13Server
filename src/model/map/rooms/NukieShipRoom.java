package model.map.rooms;


public class NukieShipRoom extends Room {

	public NukieShipRoom(int i, int[] js, double[] ds) {
		super(i, "Nuclear Ship", "Nuke", 16, 13, 2, 1, js, ds);
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
