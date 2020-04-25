package clientview.strategies;

import clientlogic.GameData;
import clientlogic.Room;
import clientview.components.MapPanel;
import clientview.OverlaySprite;
import model.items.general.GameItem;

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
        int xOffset = 0;
        int yOffset = 0;
        int viewPortWith = (int)(getWidth() / Room.getXScale());
        int viewPortHeight = (int)(getHeight() / Room.getYScale());
        if (GameData.getInstance().playerIsInSpace()) {
            double[] coords = GameData.getInstance().getSpaceCoordinates();
            xOffset = (int)coords[0] - viewPortWith / 2;
            yOffset = (int)coords[1] - viewPortHeight / 2;
        } else if (Room.isAutomaticScaling()) {
            int mapWidth = GameData.getInstance().getMapWidth();
            int xscale = (getWidth() / (mapWidth*zoom)) * zoom;
            int mapHeight = GameData.getInstance().getMapHeight();
            int yscale = ((getHeight() - inventoryPanelHeight()) / (mapHeight*zoom))*zoom;

            Room.setXScale((double) xscale);
            Room.setYScale((double) yscale);
            xOffset = GameData.getInstance().getMinX();// + getXTrans();
            yOffset = GameData.getInstance().getMinY();// + getYTrans();
        } else {
            Room current = GameData.getInstance().getCurrentRoom();
            if (current == null) {
                System.out.println("Current room was NULL! Current room id: " + GameData.getInstance().getCurrentPos());
                System.out.println("Character is: " + GameData.getInstance().getCharacter());
            }
            xOffset = current.getXPos() + (current.getWidth() - viewPortWith) / 2;
            yOffset = current.getYPos() + (current.getHeight() - viewPortHeight) / 2;
        }

        getBackgroundDrawingStrategy().drawBackground(g, getWidth(), getHeight());

        Set<OverlaySprite> drawnSprites = new HashSet<>();
        List<Room> roomList = getRoomsToDraw();
        drawRooms(g, roomList, drawnSprites, xOffset, yOffset, getXTrans(), inventoryPanelHeight() + getYTrans());

        // Draw overlay sprites which did not belong to any drawn rooms.
        for (OverlaySprite sp : GameData.getInstance().getOverlaySprites()) {
            if (!drawnSprites.contains(sp)) {
                sp.drawYourself(g, xOffset, yOffset, getXTrans(), inventoryPanelHeight() + getYTrans(), GameData.getInstance().getCurrentZ());
            }
        }

        if (!GameData.getInstance().playerIsInSpace()) {
            for (Room r : roomList) {
                r.drawFrameIfSelected(g, xOffset, yOffset, getXTrans(), inventoryPanelHeight() + getYTrans(),
                        GameData.getInstance().getCurrentPos() == r.getID());
            }
        }
    }

    private List<Room> getRoomsToDraw() {
        List<Room> roomList = new ArrayList<>();
        roomList.addAll(GameData.getInstance().getMiniMap());
        Collections.sort(roomList);
        return roomList;
    }


    public void drawRooms(Graphics g, List<Room> roomList, Set<OverlaySprite> drawnSprites,
                          int xOffset, int yOffset, int xOffPx, int yOffPx) {
        int currZ = GameData.getInstance().getCurrentZ() + MapPanel.getZTranslation();
        for (Room r : roomList) {
            if (r.getZPos() == currZ) {
                boolean shadow = !GameData.getInstance().isASelectableRoom(r.getID());
                getRoomDrawingStrategy(r).drawRoom(r, g, GameData.getInstance().getSelectableRooms().contains(r.getID()),
                        GameData.getInstance().getCurrentPos() == r.getID(),
                        xOffset, yOffset, xOffPx, yOffPx, shadow);
            } else if (r.getZPos() < currZ) {
                getRoomDrawingStrategy(r).drawRoomFromAbove(r, g, xOffset, yOffset, xOffPx, yOffPx);
                drawnSprites.addAll(r.getOverlaySprites());
            } else { // stuff above
                drawnSprites.addAll(r.getOverlaySprites());
            }

        }

        for (Room r : roomList) {
            if (r.getZPos() == currZ) {
                boolean shadow = !GameData.getInstance().isASelectableRoom(r.getID());
                getRoomDrawingStrategy(r).drawDoors(r, g, getMapPanel(), xOffset, yOffset, xOffPx, yOffPx);
                drawnSprites.addAll(r.drawYourOverlays(g, xOffset, yOffset, xOffPx, yOffPx, shadow, currZ));
                r.drawYourEffect(g, xOffset, yOffset, xOffPx, yOffPx, shadow);
            }
        }

    }


}
