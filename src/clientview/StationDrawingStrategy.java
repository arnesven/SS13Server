package clientview;

import clientlogic.GameData;
import clientlogic.Room;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StationDrawingStrategy extends DrawingStrategy {


    private ArrayList<ImageIcon> spaceSprites;


    public StationDrawingStrategy(MapPanel mapPanel) {
        super(mapPanel);
        createSpaceSprites();
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
        drawSpace(g);

        List<Room> roomList = new ArrayList<>();
        roomList.addAll(GameData.getInstance().getMiniMap());
        Collections.sort(roomList);


        Set<OverlaySprite> drawnSprites = new HashSet<>();
        for (Room r : roomList) {
            boolean shadow = !GameData.getInstance().isASelectableRoom(r.getID());
            r.drawYourself(g, GameData.getInstance().getSelectableRooms().contains(r.getID()),
                    GameData.getInstance().getCurrentPos() == r.getID(),
                    xOffset, yOffset, 0, inventoryPanelHeight(), shadow);
            drawnSprites.addAll(r.drawYourOverlays(g, xOffset, yOffset, 0, inventoryPanelHeight(), shadow));
            r.drawYourEffect(g, xOffset, yOffset, 0, inventoryPanelHeight(), shadow);

        }

        for (Room r : roomList) {
            r.drawYourDoors(g, getMapPanel(), xOffset, yOffset, 0, inventoryPanelHeight());
        }


        // Draw overlay sprites which did not belong to any drawn rooms.
        for (OverlaySprite sp : GameData.getInstance().getOverlaySprites()) {
            if (!drawnSprites.contains(sp)) {
                sp.drawYourself(g, xOffset, yOffset, 0, inventoryPanelHeight());
            }
        }

        for (Room r : roomList) {
            r.drawYourFrame(g, xOffset, yOffset, 0, inventoryPanelHeight(),
                    GameData.getInstance().getCurrentPos() == r.getID());
        }
    }

    private void createSpaceSprites() {
        spaceSprites = new ArrayList<>();
        for (int i = 0; i < 74; i++) {
            spaceSprites.add(SpriteManager.getSprite("spacebackground" + i + "" + 0));
        }

        for (int i = 0; i < 4; i++) {
            spaceSprites.addAll(spaceSprites);
        }

        Collections.shuffle(spaceSprites);
    }

    private void drawSpace(Graphics g) {
        g.setColor(new Color(0x111199));
        g.fillRect(0, 0, getWidth(), getHeight());
        int counter = 0;
        for (int y = 0; y < getHeight(); y += spaceSprites.get(0).getIconHeight()) {
            for (int x = 0; x < getWidth(); x += spaceSprites.get(0).getIconWidth()) {
                ImageIcon ic = spaceSprites.get(counter % 98);
                counter++;
                g.drawImage(ic.getImage(), x, y, null);
            }
        }


    }

}
