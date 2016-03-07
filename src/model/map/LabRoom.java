package model.map;

import model.objects.ChemicalDispenser;
import model.objects.InfectionScanner;

public class LabRoom extends Room {

	public LabRoom(int ID, int x, int y,
			int width, int height, int[] neighbors, double[] doors) {
		super(ID, "Lab", "Lab", x, y, width, height, neighbors, doors);
		
		this.addObject(new InfectionScanner());
		this.addObject(new ChemicalDispenser("Lab Storage", 2));
	}

}
