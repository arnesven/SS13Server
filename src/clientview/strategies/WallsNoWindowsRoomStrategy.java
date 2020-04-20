package clientview.strategies;

import clientlogic.Room;
import clientview.components.MapPanel;

import java.awt.*;

public class WallsNoWindowsRoomStrategy extends RoomDrawingStrategy {

    @Override
    public void drawRoom(Room r, Graphics g, boolean selectable, boolean isSelected,
                         int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow) {
        r.drawYourself(g, selectable, isSelected, xOffset, yOffset, xOffPx, yOffPx, shadow, true, false);
    }

    @Override
    public void drawRoomFromAbove(Room r, Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx) {
        r.drawYourselfFromAbove(g, xOffset, yOffset, xOffPx, yOffPx, false);
    }

    @Override
    public void drawDoors(Room r, Graphics g, MapPanel mapPanel, int xOffset, int yOffset, int xoffPx, int yOffPx) {
        r.drawYourDoors(g, mapPanel, xOffset, yOffset, xoffPx, yOffPx);
    }

}
