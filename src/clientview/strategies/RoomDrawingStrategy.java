package clientview.strategies;


import clientlogic.Room;
import clientview.OverlaySprite;
import clientview.components.MapPanel;

import java.awt.*;
import java.util.Set;

public abstract class RoomDrawingStrategy {

    public abstract void drawRoom(Room r, Graphics g, boolean selectable, boolean isSelected,
                                  int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow);

    public abstract void drawRoomFromAbove(Room r, Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx);

    public abstract void drawDoors(Room r, Graphics g, MapPanel mapPanel, int xOffset, int yOffset, int xOffPx, int inventoryHeight, int currZ);

    public abstract void drawSprites(Room r, Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow, int currZ, Set<OverlaySprite> drawnSprites);
}
