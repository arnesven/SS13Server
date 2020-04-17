package clientlogic;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientview.components.MapPanel;
import clientview.components.MyLabel;
import clientview.OverlaySprite;
import clientview.SpriteManager;
import clientview.components.MyPopupMenu;
import util.Logger;
import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Room extends MouseInteractable implements Comparable<Room> {

    public static final Color WALL_COLOR = new Color(0x303030);
    private static final Color SELECTED_ROOM_COLOR = Color.YELLOW;
    private static double DOOR_SIZE = 0.5;
    private static boolean automaticScaling = true;
    private final String floorSpriteBaseName;
    private final String backgroundType;
    private final String roomStyle;

    private int width;
    private int height;
    private int xPos;
    private int yPos;
    private int zPos;
    private String name;
    private boolean highLight = true;
    private int ID;
    private static final Color backgroundColor = new Color(0x999999);
    private boolean selectable = false;
    private static double xscale = 32;
    private static double yscale = 32;
    private ClientDoor[] doors;

    private String effectName;
    private JPopupMenu popupMenu;

    //private Color originalBackgroundColor;


    public Room(int ID, String name, String effectName, int x, int y, int z, int width, int height,
                ClientDoor[] doors, String color, String appearence) {
        this.ID = ID;
        this.name = name;
        this.effectName = effectName;
        xPos = x;
        yPos = y;
        zPos = z;
        this.width = width;
        this.height = height;
        highLight = false;
        this.doors = doors;
        floorSpriteBaseName = color;
        this.backgroundType = appearence.split("-")[1];
        this.roomStyle = appearence.split("-")[0];
    }

    public static void setAutomaticScaling(boolean b) {
        automaticScaling = b;
    }

    public static boolean isAutomaticScaling() {
        return automaticScaling;
    }


    protected boolean isSelectable() {
        return selectable;
    }


    public void drawYourself(Graphics g, boolean selectable, boolean selected, int xOffset,
                             int yOffset, int xOffPx, int yOffPx, boolean shadow, boolean withWalls) {
        if (selectable) {
            this.selectable = selectable;
        }
        ImageIcon background = SpriteManager.getSprite(floorSpriteBaseName);

        int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
        int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
        int finalW = getScaledWidthPX();
        int finalH = getScaledHeightPX();
        super.setHitBox(x, y, getZPos(), finalW, finalH);

        int startSpritePaint = 0;
        if (withWalls) {
            startSpritePaint++;
        }

        if (!shadow) {
            drawFloors(g, startSpritePaint, background, x, y);
        }

        if (withWalls) {
            drawWalls(g, x, y, finalW, finalH, background, shadow);
        }

        if (shadow) {
            g.setColor(Color.BLACK);
            g.fillRect(x + background.getIconWidth(), y + background.getIconHeight(),
                finalW-MapPanel.getZoom(), finalH-MapPanel.getZoom());
        }
    }

    public void drawYourselfFromAbove(Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx) {
        int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
        int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
        int finalW = getScaledWidthPX();
        int finalH = getScaledHeightPX();
        super.setHitBox(x, y, getZPos(), finalW, finalH);

        ImageIcon background = SpriteManager.getSprite("walldarkroof0");

        for (int row = 0; row < height * (yscale / background.getIconHeight())+1; ++row) {
            for (int col = 0; col < width * (xscale / background.getIconWidth())+1; ++col) {
                ImageIcon spriteToDraw = getAboveSprite(row == 0, row == height * (yscale / background.getIconHeight()),
                                            col == 0, col ==  width * (xscale / background.getIconWidth()));
                g.drawImage(spriteToDraw.getImage(),
                        x + col * spriteToDraw.getIconWidth(),
                        y + row * spriteToDraw.getIconHeight(), null);
            }
        }

        ImageIcon floorUnderneath = SpriteManager.getSprite(floorSpriteBaseName);
        decorateWithLeftRightWindows(g, x, y, finalW, finalH, floorUnderneath, "above");
        decorateWithBottomWindows(g, x, y, finalW, finalH, floorUnderneath);
    }

    private ImageIcon getAboveSprite(boolean rowstart, boolean rowend, boolean colstart, boolean colend) {
        String spriteName = "walldarkroof0";
        if (rowstart && colstart) {
            spriteName = "walldarkcorner0";
        } else if (rowstart && colend) {
            spriteName = "walldarkUR0";
        } else if (rowend && colstart) {
            spriteName = "walldarkLL0";
        } else if (rowend && colend) {
            spriteName = "walldarkLR0";
        } else if (colstart || colend) {
            spriteName = "walldarkside0";
        } else if (colend) {
            spriteName = "walldarkright0";
        } else if (rowend) {
            spriteName = "walldarktop0";
        }

        return SpriteManager.getSprite(spriteName);
    }

    private void drawFloors(Graphics g, int startSpritePaint, ImageIcon background, int x, int y) {
        for (int row = startSpritePaint; row < height * (yscale / background.getIconHeight()) + 1; ++row) {
            for (int col = startSpritePaint; col < width * (xscale / background.getIconWidth()) + 1; ++col) {
                ImageIcon imgToDraw = getFloorForPos(row == startSpritePaint, row == (height * (yscale / background.getIconHeight())) - 1,
                        col == startSpritePaint, col == width * (xscale / background.getIconWidth()) - 1,
                        background);

                g.drawImage(imgToDraw.getImage(),
                        x + col * imgToDraw.getIconWidth(),
                        y + row * imgToDraw.getIconHeight(), null);
            }
        }
    }


    public void drawYourEffect(Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean shadow)  {
        if (!effectName.equals("") && !shadow) {
            int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
            int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
            ImageIcon effect = SpriteManager.getSprite(effectName);
            for (int row = 0; row < height * (yscale / effect.getIconHeight()) + 1; ++row) {
                for (int col = 0; col < width * (xscale / effect.getIconWidth()) + 1; ++col) {
                    g.drawImage(effect.getImage(),
                            x + col * effect.getIconWidth(),
                            y + row * effect.getIconHeight(), null);
                }
            }
        }
    }

    public ArrayList<OverlaySprite> getOverlaySprites() {
        ArrayList<OverlaySprite> result = new ArrayList<>();
        result.addAll(GameData.getInstance().getOverlaySprites());
        result.removeIf((OverlaySprite sp) -> sp.getRoomid() != this.getID());
        return result;
    }

    public Set<OverlaySprite> drawYourOverlays(Graphics g, int xOffset, int yOffset, int xoffPX, int yoffPX, boolean shadow, int currZ) {
        Set<OverlaySprite> drawn = new HashSet<>();
        SpriteSlotTable slots = new SpriteSlotTable(this);
        if (!shadow) {
            for (OverlaySprite sp : getOverlaySprites()) {
                if (sp.getRoomid() == this.getID()) {
                    drawn.add(sp);
                    drawInOpenSlot(g, sp, slots, xOffset, yOffset, xoffPX, yoffPX, currZ);
                }
            }
        }
        slots.fillTheRest(g, this, xOffset, yOffset, xoffPX, yoffPX, currZ);

        return drawn;
    }

    private void drawInOpenSlot(Graphics g, OverlaySprite sp, SpriteSlotTable slots, int xOffset, int yOffset, int xoffPX, int yoffPX, int currZ) {
        Pair<Double, Double> slotPos = slots.getSlot(sp);
        sp.drawYourselfInRoom(g, this, slotPos, xOffset, yOffset, xoffPX, yoffPX, currZ);
    }

    public int getScaledHeightPX() {
        return (int) (getHeight() * getYScale());
    }

    private int getScaledHeight() {
        return getScaledHeightPX() / MapPanel.getZoom();
    }


    public int getScaledWidthPX() {
        return (int) (getWidth() * getXScale());
    }

    private int getScaledWidth() {
        return getScaledWidthPX() / MapPanel.getZoom();
    }


    private ImageIcon getFloorForPos(boolean rowStart, boolean rowEnd, boolean colStart, boolean colEnd, ImageIcon background) {
        String baseName = floorSpriteBaseName.substring(0, floorSpriteBaseName.length()-1);
        if (rowStart && colStart) { // UL corner
            return SpriteManager.getSprite(baseName + "UL0");
        } else if (rowStart && colEnd) { // UR corner
            return SpriteManager.getSprite(baseName + "UR0");
        } else if (rowEnd && colStart) { // LL corner
            return SpriteManager.getSprite(baseName + "LL0");
        } else if (rowEnd && colEnd) { // LR corner
            return SpriteManager.getSprite(baseName + "LR0");
        } else if (rowStart) { // TOP
            return SpriteManager.getSprite(baseName + "TOP0");
        } else if (rowEnd) { // BOTTOM
            return SpriteManager.getSprite(baseName + "BOTTOM0");
        } else if (colStart) { // LEFT
            return SpriteManager.getSprite(baseName + "LEFT0");
        } else if (colEnd) { // RIGHT
            return SpriteManager.getSprite(baseName + "RIGHT0");
        }
        return background;
    }

    private void drawWalls(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background, boolean shadow) {

            g.setColor(backgroundColor);
            ImageIcon corner = SpriteManager.getSprite("walldarkcorner0");
            g.drawImage(corner.getImage(), x, y, null);
            ImageIcon top = SpriteManager.getSprite("walldarktop0");
            for (int i = 1; i < finalW / top.getIconWidth(); ++i) {
                g.drawImage(top.getImage(), x + top.getIconWidth()*i, y, null);
                g.drawImage(top.getImage(), x+top.getIconWidth()*i, y+finalH, null);
            }
            ImageIcon left = SpriteManager.getSprite("walldarkside0");
            for (int i = 1; i < finalH / left.getIconHeight(); ++i) {
                g.drawImage(left.getImage(), x, y + left.getIconHeight()*i, null);
                g.drawImage(left.getImage(), x+finalW, y + left.getIconHeight()*i, null);
            }
            ImageIcon ll = SpriteManager.getSprite("walldarkLL0");
            g.drawImage(ll.getImage(), x, y+finalH, null);
            ImageIcon lr =  SpriteManager.getSprite("walldarkLR0");
            g.drawImage(lr.getImage(), x+finalW, y+finalH, null);
            ImageIcon ur = SpriteManager.getSprite("walldarkUR0");
            g.drawImage(ur.getImage(), x+finalW, y, null);


        if (!shadow) {
            g.setColor(backgroundColor);
        }

        g.setColor(WALL_COLOR);

        decorateWallsWithWindows(g, x, y, finalW, finalH, background, shadow);

    }

    private void decorateWallsWithPosters(Graphics g, int x, int y, int finalW, int finalH, boolean shadow) {
        ImageIcon poster = SpriteManager.getSprite("poster1left0");
        g.drawImage(poster.getImage(), x, y, null);
        poster = SpriteManager.getSprite("poster1right0");
        g.drawImage(poster.getImage(), x+poster.getIconWidth(), y, null);
    }

    private void decorateWallsWithWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background, boolean shadow) {
        decorateWithTopBottomWindows(g, x, y, finalW, finalH, background);
        decorateWithLeftRightWindows(g, x, y, finalW, finalH, background, "");
    }

    private void decorateWithLeftRightWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background, String above) {
        decorateWithLeftWindows(g, x, y, finalW, finalH, background, above);
        decorateWithRightWindows(g, x, y, finalW, finalH, background, above);

    }

    private void decorateWithRightWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background, String above) {
        int noOfWindowsX = ((finalH - MapPanel.getZoom()) / (background.getIconHeight() * 2));
        ImageIcon topright = SpriteManager.getSprite("walldarkwindowup" + above + "0");
        ImageIcon bottomright = SpriteManager.getSprite("walldarkwindowdown" + above + "0");
        for (int i = 1; i <= noOfWindowsX; ++i) {
            int ypostop = y + topright.getIconHeight() * (i * 2 - 1);
            int yposbot = y + topright.getIconHeight() * (i * 2);
            double from = (double) (i * 2 - 1) * (double) MapPanel.getZoom() / getYScale();
            double to = (double) (i * 2) * (double) MapPanel.getZoom() / getYScale();
            if (isRightSuitableForWindow(from, to)) {
                drawWindowSprite(g, topright.getImage(), x + finalW, ypostop, background);
                drawWindowSprite(g, bottomright.getImage(), x + finalW, yposbot, background);
            }
        }
    }

    private void decorateWithLeftWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background, String above) {
        int noOfWindowsX = ((finalH - MapPanel.getZoom()) / (background.getIconHeight() * 2));
        ImageIcon topleft = SpriteManager.getSprite("walldarkwindowtop" + above + "0");
        ImageIcon bottomleft = SpriteManager.getSprite("walldarkwindowbottom" + above + "0");
        for (int i = 1; i <= noOfWindowsX; ++i) {
            int ypostop = y + topleft.getIconHeight() * (i * 2 - 1);
            int yposbot = y + topleft.getIconHeight() * (i * 2);
            double from = (double) (i * 2 - 1) * (double) MapPanel.getZoom() / getYScale();
            double to = (double) (i * 2) * (double) MapPanel.getZoom() / getYScale();
            if (isLeftSuitableForWindow(from, to)) {
                drawWindowSprite(g, topleft.getImage(), x, ypostop, background);
                drawWindowSprite(g, bottomleft.getImage(), x, yposbot, background);
            }
        }
    }

    private void decorateWithTopBottomWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background) {
        decorateWithTopWindows(g, x, y, finalW, finalH, background);
        decorateWithBottomWindows(g, x, y, finalW, finalH, background);

    }

    private void decorateWithBottomWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background) {
        ImageIcon links = SpriteManager.getSprite("walldarkwindowlinks0");
        ImageIcon rechts = SpriteManager.getSprite("walldarkwindowrechts0");
        int noOfWindowsX = ((finalW - MapPanel.getZoom()) / (background.getIconWidth() * 2));
        for (int i = 1; i <= noOfWindowsX; ++i) {
            int xposleft = x + background.getIconWidth() * (i * 2 - 1);
            int xposright = x + background.getIconWidth() * (i * 2);
            double from = (double) (i * 2 - 1) * (double) MapPanel.getZoom() / getXScale();
            double to = (double) (i * 2) * (double) MapPanel.getZoom() / getXScale();
            if (isBottomSuitableForWindow(from, to)) {
                drawWindowSprite(g, links.getImage(), xposleft, y + finalH, background);
                drawWindowSprite(g, rechts.getImage(), xposright, y + finalH, background);
            }
        }
    }

    private void decorateWithTopWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background) {
        ImageIcon left = SpriteManager.getSprite("walldarkwindowleft0");
        ImageIcon right = SpriteManager.getSprite("walldarkwindowright0");
        int noOfWindowsX = ((finalW - MapPanel.getZoom()) / (left.getIconWidth() * 2));
        for (int i = 1; i <= noOfWindowsX; ++i) {
            int xposleft = x + left.getIconWidth() * (i * 2 - 1);
            int xposright = x + left.getIconWidth() * (i * 2);
            double from = (double) (i * 2 - 1) * (double) MapPanel.getZoom() / getXScale();
            double to = (double) (i * 2) * (double) MapPanel.getZoom() / getXScale();
            if (isTopSuitableForWindow(from, to)) {
                drawWindowSprite(g, left.getImage(), xposleft, y, background);
                drawWindowSprite(g, right.getImage(), xposright, y, background);
            }
        }
    }


    private void drawWindowSprite(Graphics g, Image image, int x, int y, ImageIcon background) {
        g.drawImage(background.getImage(), x, y, null);
        g.drawImage(image, x, y, null);

    }


    private boolean isLeftSuitableForWindow(double from, double to) {
        for (Room r : getRoomsOnSameZ()) {
            if (r.xPos + r.getWidth() == this.xPos) {
                if (r.yPos <= this.yPos + from && this.yPos + from <= r.yPos + r.getHeight()) {
                    return false;
                }
                if (r.yPos <= this.yPos + to && this.yPos + to <= r.yPos + r.getHeight()) {
                    return false;
                }
            }
        }

        return true;
    }

    private ArrayList<Room> getRoomsOnSameZ() {
        ArrayList<Room> list = new ArrayList<>();
        list.addAll(GameData.getInstance().getMiniMap());
        list.removeIf((Room r) -> r.getZPos() != this.getZPos());
        return list;
    }


    private boolean isRightSuitableForWindow(double from, double to) {
        for (Room r : getRoomsOnSameZ()) {
            if (r.xPos == this.xPos + this.getWidth()) {
                if (r.yPos <= this.yPos + from && this.yPos + from <= r.yPos + r.getHeight()) {
                    return false;
                }
                if (r.yPos <= this.yPos + to && this.yPos + to <= r.yPos + r.getHeight()) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean isTopSuitableForWindow(double from, double to) {
        for (Room r : getRoomsOnSameZ()) {
            if (r.yPos + r.getHeight() == this.getYPos()) {
                if (r.xPos <= this.xPos + from && this.xPos + from <= r.xPos + r.getWidth()) {
                    return false;
                }
                if (r.xPos <= this.xPos + to && this.xPos + to <= r.xPos + r.getWidth()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBottomSuitableForWindow(double from, double to) {
        for (Room r : getRoomsOnSameZ()) {
            if (r.yPos == this.getYPos() + this.getHeight()) {
                if (r.xPos <= this.xPos + from && this.xPos + from <= r.xPos + r.getWidth()) {
                    return false;
                }
                if (r.xPos <= this.xPos + to && this.xPos + to <= r.xPos + r.getWidth()) {
                    return false;
                }
            }
        }
        return true;
    }




    @Override
    protected void doOnClick(MouseEvent e) {
        this.popupMenu = new JPopupMenu();
        popupMenu.add(new MyLabel(getName()));
        popupMenu.addSeparator();
        if (GameData.getInstance().isASelectableRoom(getID())) {
            if (GameData.getInstance().getCurrentPos() == getID()) {
                JMenuItem item = new JMenuItem("Search Room");
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ServerCommunicator.send(GameData.getInstance().getClid() + " NEXTACTION " +
                                "root,General,Search Room", new MyCallback<String>() {

                            @Override
                            public void onSuccess(String result) {
                                //GameData.getInstance().setNextAction("Searching in " + getName());

                            }

                            @Override
                            public void onFail() {
                                System.out.println("Failed to send NEXTACTION (search) message to server.");
                            }
                        });

                    }
                });
                if (GameData.getInstance().getHealth() > 0.0) {
                    popupMenu.add(item);
                }
            } else {
                JMenuItem item = new JMenuItem("Move Here");
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ServerCommunicator.send(GameData.getInstance().getClid() + " NEXTACTION " +
                                "root,Move," + getName(), new MyCallback<String>() {

                            @Override
                            public void onSuccess(String result) {
                                //GameData.getInstance().setNextAction("Move to " + getName());

                            }

                            @Override
                            public void onFail() {
                                System.out.println("Failed to send NEXTACTION (move) to server");
                            }
                        });
                    }
                });
                if (GameData.getInstance().getHealth() > 0.0) {
                    popupMenu.add(item);
                }
            }
        }

       // popupMenu.show(e.getComponent(), e.getX(), e.getY());


    }

    private ClientDoor doorHitByClick(MouseEvent e) {
        if (doors != null) {
            for (ClientDoor d : doors) {
                if (d.actOnClick(e)) {
                    return d;
                }
            }
        }
        return null;
    }

    @Override
    protected void doOnHover(MouseEvent e, MapPanel mapPanel) {
        mapPanel.setToolTipText(getName());
    }


    public void drawYourDoors(Graphics g, MapPanel mapPanel, int xOffset, int yOffset, int xOffPx, int yOffPx) {
            if (doors == null) {
                   return;
            }
            for (int i = 0; i < doors.length; i++) {
                doors[i].drawYourself(g, this, xOffset, yOffset, xOffPx, yOffPx);
            }
    }

    public void drawFrameIfSelected(Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean selected) {
        int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
        int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
        int finalW = (int) (getWidth() * getXScale()) + MapPanel.getZoom();
        int finalH = (int) (getHeight() * getYScale()) + MapPanel.getZoom();
        if (selected && getZPos() == GameData.getInstance().getCurrentZ() + MapPanel.getZTranslation()) {
            ((Graphics2D)g).setStroke(new BasicStroke(4));
            g.setColor(SELECTED_ROOM_COLOR);
            g.drawRect(x, y, finalW, finalH);
        }

        ((Graphics2D)g).setStroke(new BasicStroke(1));
    }

    private boolean isTopBottomDoor(double doorX, double doorY) {
        if (doorY == this.yPos || doorY == this.yPos+this.height) {
            return true;
        }
        return false;

    }


    public static double getXScale() {
            return xscale;
        }

        public static double getYScale() {
            return yscale;
        }

        public static void setXScale(double d) {
            d = Math.max(d, 64.0);
            if (d != xscale) {
                Logger.log("New x-scale is: " + d);
                xscale = d;
            }
        }

        public static void setYScale(double d) {
            d = Math.max(d, 64.0);
            if (d != yscale) {
                Logger.log("New y-scale is " + d);
                yscale = d;
            }
        }

        protected void setHighLight(boolean b) {
            this.highLight = b;
        }

        protected boolean getHighLight() {
            return highLight;
        }

        public int getXPos() {
            return xPos;
        }

        public int getWidth() {
            return width;
        }

        public int getYPos() {
            return yPos;
        }

        public int getHeight() {
            return height;
        }

        public int getZPos() {
            return zPos;
        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return this.name;
        }

        public void setHighlight(boolean b) {
            this.highLight = b;
        }


    @Override
    public int compareTo(Room o) {
        return this.getComparissionScore() - o.getComparissionScore();
    }

    private int getComparissionScore() {
        return this.zPos*100000 + this.yPos*1000 + this.xPos;
    }


    public String getBackgroundType() {
        return backgroundType;
    }

    public String getRoomStyle() {
        return roomStyle;
    }


    public ClientDoor[] getDoors() {
        return doors;
    }

    public String getFloorSpriteBaseName() {
        return floorSpriteBaseName;
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }
}


