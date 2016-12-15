package model.map.rooms;

import model.items.general.FireExtinguisher;
import model.objects.general.Dumbwaiter;
import model.objects.general.CookOMatic;

public class KitchenRoom extends Room {

	public KitchenRoom(int i, int j, int k, int l, int m, int[] ns, double[] ds) {
		super(i, "Kitchen", "Kitch", j, k, l, m, ns, ds, RoomType.support);
		
		this.addObject(new CookOMatic(this));
        this.addObject(new Dumbwaiter(this));
        this.addItem(new FireExtinguisher());
	}

}
