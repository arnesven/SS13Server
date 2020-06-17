package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.npcs.DRBOTNPC;
import model.npcs.RecyclotronNPC;
import model.objects.general.StasisPod;
import model.objects.general.SurgeryTable;
import model.objects.general.MedkitDispenser;

/**
 * Created by erini02 on 15/12/16.
 */
public class SickbayRoom extends ScienceRoom {
    public SickbayRoom(int id,  int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Sickbay", "Sick", x, y, w, h, ints, doubles);
        addObject(new MedkitDispenser(3, this), RelativePositions.LOWER_RIGHT_CORNER);
        addObject(new StasisPod(this), RelativePositions.MID_TOP);
        addObject(new SurgeryTable(this), RelativePositions.CENTER);

    }

    @Override
    protected String getWallAppearence() {
        return "light";
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        gameData.addNPC(new DRBOTNPC(this));
    }

}
