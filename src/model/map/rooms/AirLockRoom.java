package model.map.rooms;

import model.objects.OxyMaskDispenser;
import model.objects.general.AirlockPanel;

public class AirLockRoom extends Room {

	public AirLockRoom(int ID, int number, int x, int y,
			int width, int height, int[] neighbors, double[] doors) {
		super(ID, "Air Lock #"+number         , number+"" , 
				x, y, width, height, neighbors, doors, RoomType.airlock);
		
		this.addObject(new AirlockPanel(this));
        this.addObject(new OxyMaskDispenser(this));
	}

}
