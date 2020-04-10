package model.map.rooms;

import model.GameData;
import model.items.general.FireExtinguisher;
import model.items.general.Tools;
import model.npcs.NPC;
import model.npcs.animals.ChimpNPC;
import model.objects.decorations.GreenHousePlant;
import model.objects.general.SoilPatch;
import model.objects.general.BioScanner;
import util.MyRandom;

public class GreenhouseRoom extends ScienceRoom {

	public GreenhouseRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ns,
                          double[] ds) {
		super(id, "Greenhouse"          , "GH"     , x, y, w, h, ns, ds);
		this.addItem(new FireExtinguisher());
		this.addItem(new Tools());
        this.addObject(new BioScanner(this));
        this.addObject(new SoilPatch(this));
        NPC chimp = new ChimpNPC(this);
        gameData.addNPC(chimp);

        for (int i = 0; i < MyRandom.nextInt(4); ++i) {
        	this.addObject(new GreenHousePlant(this));
		}
	}
	
}
