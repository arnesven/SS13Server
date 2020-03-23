package model.map.rooms;

import model.objects.general.StasisPod;
import model.objects.general.SurgeryTable;
import model.objects.general.MedkitDispenser;

/**
 * Created by erini02 on 15/12/16.
 */
public class SickbayRoom extends ScienceRoom {
    public SickbayRoom(int id,  int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Sickbay", "Sick", x, y, w, h, ints, doubles);
        addObject(new MedkitDispenser(3, this));
        addObject(new StasisPod(this));
        addObject(new SurgeryTable(this));
    }
}
