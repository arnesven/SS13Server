package model.map.rooms;

import model.GameData;
import model.items.general.FireExtinguisher;
import model.items.general.Tools;
import model.items.tools.CraftingTools;
import model.items.tools.RepairTools;
import model.map.doors.Door;
import model.npcs.NPC;
import model.npcs.animals.ChimpNPC;
import model.objects.decorations.GreenHousePlant;
import model.objects.general.SeedVendingMachine;
import model.objects.general.SoilPatch;
import model.objects.general.BioScanner;
import util.MyRandom;

import java.util.List;

public class GreenhouseRoom extends ScienceRoom {

	public GreenhouseRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ns,
                          Door[] ds) {
		super(id, "Greenhouse"          , "GH"     , x, y, w, h, ns, ds);
		this.addItem(new FireExtinguisher());
		this.addItem(new RepairTools());
		this.addItem(new CraftingTools());
		this.addObject(new BioScanner(this), RelativePositions.MID_LEFT);
        this.addObject(new SoilPatch(this), RelativePositions.CENTER);
		this.addObject(new SeedVendingMachine(this), RelativePositions.MID_RIGHT);
		NPC chimp = new ChimpNPC(this);
        gameData.addNPC(chimp);

        for (int i = 0; i < MyRandom.nextInt(10)+5; ++i) {
        	RelativePositions relPos = MyRandom.sample(List.of(RelativePositions.UPPER_RIGHT_CORNER,
					RelativePositions.UPPER_LEFT_CORNER, RelativePositions.LOWER_LEFT_CORNER));
        	this.addObject(new GreenHousePlant(this), relPos);
		}
	}

	@Override
	protected String getWallAppearence() {
		return "light";
	}
}
