package clientview;

import clientlogic.GameData;
import clientlogic.Room;

import java.awt.*;
import java.util.*;
import java.util.List;

public class StationDrawingStrategy extends DrawingStrategy {



    public StationDrawingStrategy(MapPanel mapPanel) {
        super(mapPanel, new DrawSpaceBackgroundStrategy());
    }


    @Override
    public void paint(Graphics g) {
        int zoom = getZoom();
        int xOffset;
        int yOffset;
        if (Room.isAutomaticScaling()) {
            int mapWidth = GameData.getInstance().getMapWidth();
            int xscale = (getWidth() / (mapWidth*zoom)) * zoom;
            int mapHeight = GameData.getInstance().getMapHeight();
            int yscale = ((getHeight() - inventoryPanelHeight()) / (mapHeight*zoom))*zoom;

            Room.setXScale((double) xscale);
            Room.setYScale((double) yscale);
            xOffset = GameData.getInstance().getMinX();
            yOffset = GameData.getInstance().getMinY();
        } else {
            xOffset = getXTrans();
            yOffset = getYTrans();
        }

        getBackgroundDrawingStrategy().drawBackground(g, getWidth(), getHeight());

        Set<OverlaySprite> drawnSprites = new HashSet<>();
        List<Room> roomList = getRoomsToDraw();
        drawRooms(g, roomList, drawnSprites, xOffset, yOffset);

        // Draw overlay sprites which did not belong to any drawn rooms.
        for (OverlaySprite sp : GameData.getInstance().getOverlaySprites()) {
            if (!drawnSprites.contains(sp)) {
                sp.drawYourself(g, xOffset, yOffset, 0, inventoryPanelHeight());
            }
        }

        for (Room r : roomList) {
            r.drawFrameIfSelected(g, xOffset, yOffset, 0, inventoryPanelHeight(),
                    GameData.getInstance().getCurrentPos() == r.getID());
        }
    }

    private List<Room> getRoomsToDraw() {
        List<Room> roomList = new ArrayList<>();
        roomList.addAll(GameData.getInstance().getMiniMap());
        Collections.sort(roomList);
        return roomList;
    }

    private void drawRooms(Graphics g, List<Room> roomList, Set<OverlaySprite> drawnSprites, int xOffset, int yOffset) {
        int currZ = GameData.getInstance().getCurrentZ() + MapPanel.getZTranslation();
        for (Room r : roomList) {
            if (r.getZPos() == currZ) {
                boolean shadow = !GameData.getInstance().isASelectableRoom(r.getID());
                r.drawYourself(g, GameData.getInstance().getSelectableRooms().contains(r.getID()),
                        GameData.getInstance().getCurrentPos() == r.getID(),
                        xOffset, yOffset, 0, inventoryPanelHeight(), shadow);
                r.drawYourDoors(g, getMapPanel(), xOffset, yOffset, 0, inventoryPanelHeight());
                drawnSprites.addAll(r.drawYourOverlays(g, xOffset, yOffset, 0, inventoryPanelHeight(), shadow));
                r.drawYourEffect(g, xOffset, yOffset, 0, inventoryPanelHeight(), shadow);
            } else if (r.getZPos() < currZ) {
                r.drawYourselfFromAbove(g, xOffset, yOffset, inventoryPanelHeight());
                drawnSprites.addAll(r.getOverlaySprites());
            } else { // stuff above
                drawnSprites.addAll(r.getOverlaySprites());
            }

        }

    }


}