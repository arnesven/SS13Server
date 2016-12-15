package model.map.rooms;

import model.GameData;
import model.npcs.BAR2D2Robot;
import model.npcs.robots.RobotNPC;
import model.objects.Cabinet;
import model.objects.general.SlotMachine;

/**
 * Created by erini02 on 28/04/16.
 */
public class BarRoom extends Room {
    public BarRoom(GameData gameData, int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType support) {
        super(i, "Bar", "Bar", i1, i2, i3, i4, ints, doubles, support);
        this.addObject(new Cabinet(this));
        this.addObject(new SlotMachine(this));
        RobotNPC bar2d2 = new BAR2D2Robot(this.getID(), this);
        gameData.addNPC(bar2d2);
    }
}
