package model.map.rooms;

import model.items.weapons.*;
import model.items.general.FragGrenade;
import model.map.doors.Door;
import model.objects.general.DispenserObject;
import model.objects.general.GameObject;

public class ArmoryRoom extends SecurityRoom {

	public ArmoryRoom(int ID, int x, int y,
                      int width, int height, int[] neighbors, Door[] doors) {
		super(ID, "Armory", "Army", x, y, width, height, neighbors, doors);
		this.addObject(new ArmoryStorage("Armory Storage", this), RelativePositions.LOWER_LEFT_CORNER);
		this.addItem(new Flamer());
		this.addItem(new Flamer());
		this.addItem(new Revolver());
		this.addItem(new Revolver());
		this.addItem(new Shotgun());
		this.addItem(new LaserPistol());
	}

	private class ArmoryStorage extends DispenserObject {
		public ArmoryStorage(String name, Room pos) {
			super(name, pos);
			getInventory().add(new FragGrenade());
			getInventory().add(new FragGrenade());
			getInventory().add(new ShotgunShells());
			getInventory().add(new ShotgunShells());
			getInventory().add(new ShotgunShells());
			getInventory().add(new RevolverAmmo());
			getInventory().add(new RevolverAmmo());
		}
	}
}
