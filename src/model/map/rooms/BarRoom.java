package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.npcs.BAR2D2Robot;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.BarSignControl;
import model.objects.decorations.JukeBox;
import model.objects.general.Refrigerator;
import model.objects.general.SlotMachine;

/**
 * Created by erini02 on 28/04/16.
 */
public class BarRoom extends SupportRoom {
    public BarRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doors) {
        super(id, "Bar", "Bar", x, y, w, h, ints, doors);
        this.addObject(new Refrigerator(this));
        this.addObject(new SlotMachine(this));
        this.addObject(new BarSignControl(this));
        this.addObject(new JukeBox(this));
        RobotNPC bar2d2 = new BAR2D2Robot(this.getID(), this);
        gameData.addNPC(bar2d2);
    }
}
