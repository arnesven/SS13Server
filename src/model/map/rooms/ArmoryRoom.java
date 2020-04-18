package model.map.rooms;

import model.items.weapons.Flamer;
import model.items.general.Grenade;
import model.items.weapons.LaserPistol;
import model.items.weapons.Revolver;
import model.map.doors.Door;

public class ArmoryRoom extends SecurityRoom {

	public ArmoryRoom(int ID, int x, int y,
                      int width, int height, int[] neighbors, Door[] doors) {
		super(ID, "Armory", "Army", x, y, width, height, neighbors, doors);
		this.addItem(new Flamer());
		this.addItem(new Flamer());
		this.addItem(new Revolver());
		this.addItem(new LaserPistol());
		this.addItem(new Grenade());
	}

}
