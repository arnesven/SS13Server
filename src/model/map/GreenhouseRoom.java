package model.map;

import model.items.FireExtinguisher;
import model.items.Tools;

public class GreenhouseRoom extends Room {

	public GreenhouseRoom(int i, int j, int k, int l, int m, int[] ns,
			double[] ds) {
		super(i, "Greenhouse"          , "GH"     , j, k, l, m, ns, ds);
	
		this.addItem(new FireExtinguisher());
		this.addItem(new Tools());
	}
	
}
