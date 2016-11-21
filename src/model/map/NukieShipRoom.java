package model.map;


public class NukieShipRoom extends Room {

	public NukieShipRoom(int i, int[] js, double[] ds) {
		super(i, "Nuclear Ship", "Nuke", 16, 13, 2, 1, js, ds, RoomType.hidden);
	}
	
}
