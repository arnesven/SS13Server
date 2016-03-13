package model.map;

import model.objects.CookOMatic;

public class KitchenRoom extends Room{

	public KitchenRoom(int i, int j, int k, int l, int m, int[] ns, double[] ds) {
		super(i, "Kitchen", "Kitch", j, k, l, m, ns, ds);
		
		this.addObject(new CookOMatic());
	}

}
