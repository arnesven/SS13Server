package model.map.rooms;

import model.GameData;
import model.items.general.FireExtinguisher;
import model.items.general.Tools;
import model.map.doors.Door;
import model.npcs.NPC;
import model.npcs.animals.ChimpNPC;
import model.objects.decorations.GreenHousePlant;
import model.objects.general.SeedVendingMachine;
import model.objects.general.SoilPatch;
import model.objects.general.BioScanner;
import util.MyRandom;

public class GreenhouseRoom extends ScienceRoom {

	public GreenhouseRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ns,
                          Door[] ds) {
		super(id, "Greenhouse"          , "GH"     , x, y, w, h, ns, ds);
		this.addItem(new FireExtinguisher());
		this.addItem(new Tools());
        this.addObject(new BioScanner(this));
        this.addObject(new SoilPatch(this));
		this.addObject(new SeedVendingMachine(this));
		NPC chimp = new ChimpNPC(this);
        gameData.addNPC(chimp);

        for (int i = 0; i < MyRandom.nextInt(10)+5; ++i) {
        	this.addObject(new GreenHousePlant(this));
		}
	}

	@Override
	protected String getWallAppearence() {
		return "light";
	}
}
