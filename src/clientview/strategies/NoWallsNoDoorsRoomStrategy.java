package clientview.strategies;

import clientlogic.Room;
import clientview.OverlaySprite;
import clientview.components.MapPanel;

import java.awt.*;
import java.util.Set;

public class NoWallsNoDoorsRoomStrategy extends RoomDrawingStrategy {
    @Override
    public void drawRoom(Room r, Graphics g, boolean selectable, boolean isSelected, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow) {
        r.drawYourself(g, selectable, isSelected, xOffset, yOffset, xOffPx, yOffPx, shadow, false, false);
    }

    @Override
    public void drawRoomFromAbove(Room r, Graphics g, int xOffset, int yOffset, int xOffpx, int yOffPx) {
        //r.drawYourselfFromAbove(g, xOffset, yOffset, xOffpx, inventoryHeight, false);
        r.drawYourself(g, false, false, xOffset, yOffset, xOffpx, yOffPx, false, false, false);
    }

    @Override
    public void drawDoors(Room r, Graphics g, MapPanel mapPanel, int xOffset, int yOffset, int xoffpx, int inventoryHeight, int currZ) {

    }

    @Override
    public void drawSprites(Room r, Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow, int currZ, Set<OverlaySprite> drawnSprites) {
        if (r.getZPos() <= currZ) {
            r.drawYourOverlays(g, xOffset, yOffset, xOffPx, yOffPx, shadow, currZ);
            drawnSprites.addAll(r.drawYourOverlays(g, xOffset, yOffset, xOffPx, yOffPx, shadow, currZ));
        }
    }
}
