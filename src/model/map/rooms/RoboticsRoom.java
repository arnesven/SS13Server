package model.map.rooms;

import model.map.doors.Door;
import model.map.doors.UpgoingStairsDoor;
import model.objects.consoles.BotConsole;

public class RoboticsRoom extends TechRoom {
    public RoboticsRoom(int id, int x, int y, int w, int h, int[] neigh, Door[] doors) {
        super(id, "Robotics", "", x, y, w, h, neigh, doors);
        addObject(new BotConsole(this));
        addObject(new UpgoingStairsDoor(this));
    }
}
