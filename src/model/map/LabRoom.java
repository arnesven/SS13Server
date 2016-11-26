package model.map;

import model.items.general.FireExtinguisher;
import model.items.suits.RadiationSuit;
import model.objects.consoles.TeleportConsole;
import model.objects.general.ChemicalDispenser;
import model.objects.general.BioScanner;
import model.objects.consoles.GeneticsConsole;

public class LabRoom extends Room {

	public LabRoom(int ID, int x, int y,
			int width, int height, int[] neighbors, double[] doors) {
		super(ID, "Lab", "Lab", x, y, width, height, neighbors, doors, RoomType.science);

		this.addObject(new ChemicalDispenser("Lab Storage", 2, this));
		this.addObject(new GeneticsConsole(this));
        this.addObject(new TeleportConsole(this));
        this.addItem(new FireExtinguisher());
        this.addItem(new RadiationSuit());

	}
}
