package model.map.rooms;

import model.objects.general.StasisPod;
import model.objects.general.SurgeryTable;
import model.objects.general.MedkitDispenser;

/**
 * Created by erini02 on 15/12/16.
 */
public class SickbayRoom extends Room {
    public SickbayRoom(int i,  int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType science) {
        super(i, "Sickbay", "Sick", i1, i2, i3, i4, ints, doubles, science);
        addObject(new MedkitDispenser(3, this));
        addObject(new StasisPod(this));
        addObject(new SurgeryTable(this));
    }
}
