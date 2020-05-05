package model.map.rooms;

import model.items.general.FireExtinguisher;
import model.items.suits.RadiationSuit;
import model.map.doors.Door;
import model.objects.consoles.GeneratorConsole;
import model.objects.consoles.TeleportConsole;
import model.objects.general.ChemicalApparatus;
import model.objects.general.ChemicalDispenser;
import model.objects.consoles.GeneticsConsole;
import model.objects.general.CloneOMatic;

public class LabRoom extends ScienceRoom {

	public LabRoom(int ID, int x, int y,
			int width, int height, int[] neighbors, Door[] doors) {
		super(ID, "Lab", "Lab", x, y, width, height, neighbors, doors);

		this.addObject(new ChemicalDispenser("Lab Storage", 2, this));
        GeneticsConsole gc = new GeneticsConsole(this);
		this.addObject(gc);
        this.addObject(new TeleportConsole(this));
        this.addObject(new ChemicalApparatus(this));
        this.addObject(new CloneOMatic(this, gc));
        this.addItem(new RadiationSuit());
	}

	@Override
	protected String getWallAppearence() {
		return "light";
	}
}
