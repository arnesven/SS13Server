package clientview.strategies;

import clientlogic.Room;
import clientview.components.MapPanel;

import java.awt.*;

public class NoWallsNoDoorsRoomStrategy extends RoomDrawingStrategy {
    @Override
    public void drawRoom(Room r, Graphics g, boolean selectable, boolean isSelected, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow) {
        r.drawYourself(g, selectable, isSelected, xOffset, yOffset, xOffPx, yOffPx, shadow, false);
    }

    @Override
    public void drawRoomFromAbove(Room r, Graphics g, int xOffset, int yOffset, int inventoryHeight) {
        r.drawYourselfFromAbove(g, xOffset, yOffset, inventoryHeight);
    }

    @Override
    public void drawDoors(Room r, Graphics g, MapPanel mapPanel, int xOffset, int yOffset, int inventoryHeight) {

    }
}
