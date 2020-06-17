package model.map.rooms;

import model.items.general.Multimeter;
import model.items.tools.RepairTools;
import model.map.doors.Door;
import model.map.doors.UpgoingStairsDoor;
import model.objects.GlassSecuritronCage;
import model.objects.consoles.BotConsole;

public class RoboticsRoom extends TechRoom {
    public RoboticsRoom(int id, int x, int y, int w, int h, int[] neigh, Door[] doors) {
        super(id, "Robotics", "", x, y, w, h, neigh, doors);
        addObject(new BotConsole(this), RelativePositions.UPPER_LEFT_CORNER);
        addObject(new UpgoingStairsDoor(this), RelativePositions.UPPER_RIGHT_CORNER);
        addObject(new GlassSecuritronCage(this), RelativePositions.MID_BOTTOM);
        addItem(new RepairTools());
        addItem(new Multimeter());
    }
}
