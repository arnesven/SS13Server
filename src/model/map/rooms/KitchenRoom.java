package model.map.rooms;

import model.items.general.FireExtinguisher;
import model.objects.general.Dumbwaiter;
import model.objects.general.CookOMatic;

public class KitchenRoom extends SupportRoom {

	public KitchenRoom(int id, int x, int y, int width, int height, int[] ns, double[] ds) {
		super(id, "Kitchen", "Kitch", x, y, width, height, ns, ds);
		
		this.addObject(new CookOMatic(this));
        this.addObject(new Dumbwaiter(this));
        this.addItem(new FireExtinguisher());
	}

}
