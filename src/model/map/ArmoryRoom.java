package model.map;

import model.items.weapons.Flamer;
import model.items.Grenade;
import model.items.weapons.LaserPistol;
import model.items.weapons.Revolver;

public class ArmoryRoom extends Room {

	public ArmoryRoom(int ID, int x, int y,
			int width, int height, int[] neighbors, double[] doors) {
		super(ID, "Armory", "Army", x, y, width, height, neighbors, doors);
		this.addItem(new Flamer());
		this.addItem(new Flamer());
		this.addItem(new Revolver());
		this.addItem(new LaserPistol());
		this.addItem(new Grenade());
	}

}
