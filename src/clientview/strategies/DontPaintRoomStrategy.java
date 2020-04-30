package clientview.strategies;

import clientlogic.Room;
import clientview.OverlaySprite;
import clientview.components.MapPanel;

import java.awt.*;
import java.util.Set;

public class DontPaintRoomStrategy extends RoomDrawingStrategy {
    @Override
    public void drawRoom(Room r, Graphics g, boolean selectable, boolean isSelected, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow) {

    }

    @Override
    public void drawRoomFromAbove(Room r, Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx) {

    }

    @Override
    public void drawDoors(Room r, Graphics g, MapPanel mapPanel, int xOffset, int yOffset, int xOffPx, int inventoryHeight, int currZ) {

    }

    @Override
    public void drawSprites(Room r, Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow, int currZ, Set<OverlaySprite> drawnSprites) {

    }
}
