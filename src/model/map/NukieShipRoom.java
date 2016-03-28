package model.map;

import model.items.weapons.Revolver;

public class NukieShipRoom extends Room {

	public NukieShipRoom(int i, int[] js, double[] ds) {
		super(i, "Nuclear Ship", "", 10, 0, 0, 0, js, ds);
		
		this.addItem(new Revolver());
		this.addItem(new Revolver());

	}
	
}
