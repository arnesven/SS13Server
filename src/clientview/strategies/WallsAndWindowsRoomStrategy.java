package clientview.strategies;

import clientlogic.Room;

import java.awt.*;

public class WallsAndWindowsRoomStrategy extends RoomDrawingStrategy {
    @Override
    public void drawRoom(Room r, Graphics g, boolean selectable, boolean isSelected, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow) {
        r.drawYourself(g, selectable, isSelected, xOffset, yOffset, xOffPx, yOffPx, shadow);
    }

    @Override
    public void drawRoomFromAbove(Room r, Graphics g, int xOffset, int yOffset, int inventoryHeight) {
        r.drawYourselfFromAbove(g, xOffset, yOffset, inventoryHeight);
    }
}
