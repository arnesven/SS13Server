package model.map.rooms;

import model.GameData;
import model.items.general.FireExtinguisher;
import model.items.general.Tools;
import model.npcs.NPC;
import model.npcs.animals.ChimpNPC;
import model.objects.general.SoilPatch;
import model.objects.general.BioScanner;

public class GreenhouseRoom extends Room {

	public GreenhouseRoom(GameData gameData, int i, int j, int k, int l, int m, int[] ns,
                          double[] ds) {
		super(i, "Greenhouse"          , "GH"     , j, k, l, m, ns, ds, RoomType.science);
		this.addItem(new FireExtinguisher());
		this.addItem(new Tools());
        this.addObject(new BioScanner(this));
        this.addObject(new SoilPatch(this));
        NPC chimp = new ChimpNPC(this);
        gameData.addNPC(chimp);
	}
	
}
