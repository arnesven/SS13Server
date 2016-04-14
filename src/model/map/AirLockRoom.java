package model.map;

import model.objects.general.PressurePanel;

public class AirLockRoom extends Room {

	public AirLockRoom(int ID, int number, int x, int y,
			int width, int height, int[] neighbors, double[] doors) {
		super(ID, "Air Lock #"+number         , number+"" , 
				x, y, width, height, neighbors, doors);
		
		this.addObject(new PressurePanel(this));
	}

}
