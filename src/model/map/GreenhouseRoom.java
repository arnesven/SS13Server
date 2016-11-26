package model.map;

import model.items.general.FireExtinguisher;
import model.items.general.Tools;
import model.objects.general.BioScanner;

public class GreenhouseRoom extends Room {

	public GreenhouseRoom(int i, int j, int k, int l, int m, int[] ns,
			double[] ds) {
		super(i, "Greenhouse"          , "GH"     , j, k, l, m, ns, ds, RoomType.science);
	
		this.addItem(new FireExtinguisher());
		this.addItem(new Tools());
        this.addObject(new BioScanner(this));
	}
	
}
