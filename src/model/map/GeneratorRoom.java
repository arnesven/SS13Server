package model.map;

import model.items.suits.FireSuit;
import model.objects.ChemicalDispenser;
import model.objects.GeneratorConsole;

public class GeneratorRoom extends Room {

	public GeneratorRoom(int ID, int x, int y,
			int width, int height, int[] neighbors, double[] doors) {
		super(ID, "Generator"           , "Gen"    , x,  y, width, height, neighbors, doors );
		this.addObject(new ChemicalDispenser("Fuel Storage", 2, this));
		this.addItem(new FireSuit());
		this.addObject(new GeneratorConsole(this));
	}

}
