package model.map;

import model.objects.consoles.BotConsole;
import model.objects.general.ChemicalDispenser;
import model.objects.general.BioScanner;
import model.objects.consoles.GeneticsConsole;

public class LabRoom extends Room {

	public LabRoom(int ID, int x, int y,
			int width, int height, int[] neighbors, double[] doors) {
		super(ID, "Lab", "Lab", x, y, width, height, neighbors, doors);
		
		this.addObject(new BioScanner(this));
		this.addObject(new ChemicalDispenser("Lab Storage", 2, this));
		this.addObject(new GeneticsConsole(this));

	}

}
