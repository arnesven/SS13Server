package clientview.strategies;


import clientlogic.Room;

import java.awt.*;

public abstract class RoomDrawingStrategy {

    public abstract void drawRoom(Room r, Graphics g, boolean selectable, boolean isSelected,
                                  int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow);

    public abstract void drawRoomFromAbove(Room r, Graphics g, int xOffset, int yOffset, int inventoryHeight);
}
