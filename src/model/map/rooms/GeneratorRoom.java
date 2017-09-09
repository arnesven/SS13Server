package model.map.rooms;

import model.GameData;
import model.items.general.FireExtinguisher;
import model.items.general.Tools;
import model.items.suits.FireSuit;
import model.objects.general.ChemicalDispenser;
import model.objects.consoles.GeneratorConsole;

public class GeneratorRoom extends Room {

	public GeneratorRoom(int ID, int x, int y,
                         int width, int height, int[] neighbors, double[] doors, GameData gameData) {
		super(ID, "Generator"           , "Gen"    , x,  y, width, height, neighbors, doors, RoomType.tech );
		this.addObject(new ChemicalDispenser("Storage", 2, this));
		this.addObject(new GeneratorConsole(this, gameData));

	}

}
