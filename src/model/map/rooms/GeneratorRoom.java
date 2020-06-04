package model.map.rooms;

import model.GameData;
import model.items.EmptyContainer;
import model.items.general.Multimeter;
import model.items.general.Tools;
import model.items.suits.FireSuit;
import model.items.suits.InsulatedGloves;
import model.items.tools.CraftingTools;
import model.items.tools.RepairTools;
import model.map.doors.Door;
import model.objects.consoles.LifeSupportConsole;
import model.objects.general.ChemicalDispenser;
import model.objects.consoles.GeneratorConsole;
import model.objects.power.Battery;

public class GeneratorRoom extends TechRoom {

	public GeneratorRoom(int ID, int x, int y,
						 int width, int height, int[] neighbors, Door[] doors, GameData gameData) {
		super(ID, "Generator"           , "Gen"    , x,  y, width, height, neighbors, doors);
		this.addObject(new ChemicalDispenser("Storage", 2, this));
		this.addObject(new GeneratorConsole(this, gameData));
        this.addObject(new Battery(this, 0, false));
        this.addObject(new Battery(this, 0, false));
        this.addItem(new RepairTools());
        this.addItem(new CraftingTools());
        this.addItem(new Multimeter());
        this.addItem(new InsulatedGloves());
        this.addItem(new FireSuit());
        this.addItem(new EmptyContainer());
	}

}
