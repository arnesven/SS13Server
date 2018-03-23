package model.movepowers;

import model.map.rooms.Room;

import java.util.List;

public class MovePowersHandler {

    private final List<Room> rooms;
    private final int buttonWidth;
    private int min_X = Integer.MAX_VALUE;
    private int max_X = Integer.MIN_VALUE;
    private int min_Y = Integer.MAX_VALUE;
    private int max_Y = Integer.MIN_VALUE;
    private int max_ID = Integer.MIN_VALUE;
    private int numCreated = 0;
    private static int BUTTONS_PER_ROW = 20;

    public MovePowersHandler(List<Room> result, int buttonWidth) {
        this.rooms = result;
        findLimits(result);
        this.buttonWidth = buttonWidth;
    }

    private void findLimits(List<Room> result) {
        for (Room r : result) {
            if (r.getX() < min_X) {
                min_X = r.getX();
            }
            if (r.getY() < min_Y) {
                min_Y = r.getY();
            }
            if ( r.getX() + r.getWidth() > max_X) {
                max_X = r.getX() + r.getWidth();
            }
            if (r.getY() + r.getHeight() > max_Y) {
                max_Y = r.getY() + r.getHeight();
            }
            if (r.getID() > max_ID) {
                max_ID = r.getID();
            }
        }
    }

    public Room makeButton(MovePower mp) {
        Room r = new MovePowerRoom(-(numCreated+1), mp.getName(),min_X + (numCreated % BUTTONS_PER_ROW)*buttonWidth,
                max_Y+(numCreated / BUTTONS_PER_ROW), mp, buttonWidth);
        numCreated++;
        return r;
    }
}
