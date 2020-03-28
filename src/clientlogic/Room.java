package clientlogic;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientview.MapPanel;
import clientview.MyLabel;
import clientview.OverlaySprite;
import clientview.SpriteManager;
import util.Logger;
import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class Room extends MouseInteractable implements Comparable<Room> {

    public static final Color WALL_COLOR = new Color(0x303030);
    private static final Color SELECTED_ROOM_COLOR = Color.YELLOW;
    private static double DOOR_SIZE = 0.5;
    private static boolean automaticScaling = true;
    private final String floorSpriteBaseName;

    private int width;
    private int height;
    private int xPos;
    private int yPos;
    private String name;
    private boolean highLight = true;
    private int ID;
    private static final Color backgroundColor = new Color(0x999999);
    private boolean selectable = false;
    private static double xscale = 32;
    private static double yscale = 32;
    private double[] doors;

    private String effectName;

    //private Color originalBackgroundColor;


    public Room(int ID, String name, String effectName, int x, int y, int width, int height,
                double[] doors, String color) {
        this.ID = ID;
        this.name = name;
        this.effectName = effectName;
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;
        highLight = false;
        this.doors = doors;
        //originalBackgroundColor = new Color(Integer.parseInt(color.replace("#", ""), 16));
        floorSpriteBaseName = color;
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
                             int yOffset, int xOffPx, int yOffPx, boolean shadow) {

            if (selectable) {
//                g.setColor(selectableColor);
                this.selectable = selectable;
            }
//

        ImageIcon background = SpriteManager.getSprite(floorSpriteBaseName);

        int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
        int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
        int finalW = getScaledWidthPX();
        int finalH = getScaledHeightPX();

        super.setHitBox(x, y, finalW, finalH);

        int startSpritePaint = 0;
        if (!floorSpriteBaseName.contains("outdoor")) {
            startSpritePaint = 1;
            drawWalls(g, x, y, finalW, finalH, background, shadow);
        }

        if (!shadow) {
            drawFloors(g, startSpritePaint, background, x, y);

        } else {
            g.setColor(Color.BLACK);

            g.fillRect(x + background.getIconWidth(), y + background.getIconHeight(),
                finalW, finalH);
        }


    }

    private void drawFloors(Graphics g, int startSpritePaint, ImageIcon background, int x, int y) {
        for (int row = startSpritePaint; row < height * (yscale / background.getIconHeight()) + 1; ++row) {
            for (int col = startSpritePaint; col < width * (xscale / background.getIconWidth()); ++col) {
                ImageIcon imgToDraw = getFloorForPos(row == startSpritePaint, row == (height * (yscale / background.getIconHeight())),
                        col == startSpritePaint, col == width * (xscale / background.getIconWidth()),
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


    public Set<OverlaySprite> drawYourOverlays(Graphics g, int xOffset, int yOffset, int xoffPX, int yoffPX, boolean shadow) {
        Set<OverlaySprite> drawn = new HashSet<>();
        SpriteSlotTable slots = new SpriteSlotTable(this);
        if (!shadow) {
            for (OverlaySprite sp : GameData.getInstance().getOverlaySprites()) {
                if (sp.getRoomid() == this.getID()) {
                    drawn.add(sp);
                    drawInOpenSlot(g, sp, slots, xOffset, yOffset, xoffPX, yoffPX);
                }
            }
        }

        return drawn;
    }

    private void drawInOpenSlot(Graphics g, OverlaySprite sp, SpriteSlotTable slots, int xOffset, int yOffset, int xoffPX, int yoffPX) {
        Pair<Double, Double> slotPos = slots.getSlot(sp.getHash());
        sp.drawYourselfInRoom(g, this, slotPos, xOffset, yOffset, xoffPX, yoffPX);
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
            }
            ImageIcon left = SpriteManager.getSprite("walldarkside0");
            for (int i = 1; i < finalH / left.getIconHeight(); ++i) {
                g.drawImage(left.getImage(), x, y + left.getIconHeight()*i, null);
                g.drawImage(left.getImage(), x+finalW, y + left.getIconHeight()*i, null);
            }
            ImageIcon ll = SpriteManager.getSprite("walldarkLL0");
            g.drawImage(ll.getImage(), x, y+finalH, null);
            g.drawImage(ll.getImage(), x+finalW, y+finalH, null);
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
        ImageIcon left = SpriteManager.getSprite("walldarkwindowleft0");
        ImageIcon right = SpriteManager.getSprite("walldarkwindowright0");
        ImageIcon top = SpriteManager.getSprite("walldarkwindowtop0");
        ImageIcon bottom = SpriteManager.getSprite("walldarkwindowbottom0");

      //  for (int xpos = x + left.getIconWidth()/5; xpos < finalW; xpos += left.getIconWidth()*2) {
      //  if (!shadow) {
            for (int i = 1; i < (finalW / (left.getIconWidth() * 2)); ++i) {
                int xposleft = x + left.getIconWidth() * (i * 2 - 1);
                int xposright = x + left.getIconWidth() * (i * 2);

                if ((i == 0 && isBothSuitableForWindow(i)) || (i > 0 && isTopSuitableForWindow(i))) {
                    g.drawImage(left.getImage(), xposleft, y, null);
                    g.drawImage(right.getImage(), xposright, y, null);
                }
            }

            for (int i = 1; i < (finalH / (left.getIconHeight() * 2)); ++i) {
                int ypostop = y + left.getIconHeight() * (i * 2 - 1);
                int yposbot = y + left.getIconHeight() * (i * 2);

                if ((i == 0 && isBothSuitableForWindow(i)) || (i > 0 && isLeftSuitableForWindow(i))) {
                    g.drawImage(top.getImage(), x, ypostop, null);
                    g.drawImage(bottom.getImage(), x, yposbot, null);
                }
            }


       // }
      //     g.drawImage(right.getImage(), xpos+left.getIconWidth(), y+finalH, null);
    //}


    }

    private boolean isBothSuitableForWindow(int i) {
        return isLeftSuitableForWindow(i) && isTopSuitableForWindow(i);
    }

    private boolean isLeftSuitableForWindow(int i) {
        for (Room r : GameData.getInstance().getMiniMap()) {
            if (r.xPos + r.getWidth() == this.xPos) {
                if (r.yPos <= this.yPos + i && this.yPos + i <= r.yPos + r.getScaledHeight() + 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isTopSuitableForWindow(int i) {
        for (Room r : GameData.getInstance().getMiniMap()) {
            if (r.yPos + r.getHeight() == this.yPos) {
                if (r.xPos <= this.xPos + i && this.xPos + i <= r.xPos + r.getScaledWidth() + 1) {
                    return false;
                }
            }
        }

        return true;
    }


    @Override
    protected void doOnClick(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();
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
                                GameData.getInstance().setNextAction("Searching in " + getName());

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
                                GameData.getInstance().setNextAction("Move to " + getName());

                            }
                        });
                    }
                });
                if (GameData.getInstance().getHealth() > 0.0) {
                    popupMenu.add(item);
                }
            }
        }

        popupMenu.show(e.getComponent(), e.getX(), e.getY());


    }

    @Override
    protected void doOnHover(MouseEvent e, MapPanel mapPanel) {
        mapPanel.setToolTipText(getName());
    }


    public void drawYourDoors(Graphics g, MapPanel mapPanel, int xOffset, int yOffset, int xOffPx, int yOffPx) {
            if (doors == null) {
                   return;
            }
            for (int i = 0; i < doors.length; i+=2) {
                ImageIcon ic = null;
                ImageIcon ic2 = null;

                ic = SpriteManager.getSprite("normaldoor0");
                int xpos;
                if (doors[i] < 0.0) { // dummy door (for locked doors)
                    ic = SpriteManager.getSprite("lockeddoor0");
                    xpos = (int)((-doors[i]-xOffset) * getXScale()) + xOffPx;
                } else {
                    xpos = (int)((doors[i]-xOffset) * getXScale()) + xOffPx;
                }

                int ypos = (int)((doors[i+1]-yOffset) * getYScale()) + yOffPx;

                g.drawImage(ic.getImage(), xpos, ypos, null);
                g.setColor(Color.BLACK);
                //g.drawRect(xpos, ypos, width, height);
            }
    }

    public void drawYourFrame(Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean selected) {
        int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
        int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
        int finalW = (int) (getWidth() * getXScale()) + MapPanel.getZoom();
        int finalH = (int) (getHeight() * getYScale()) + MapPanel.getZoom();
        if (selected) {
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
        if (d != xscale) {
            Logger.log("New x-scale is: " + d);
            xscale = d;
        }
        }

        public static void setYScale(double d) {
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
        if (this.getYPos() == o.getYPos()) {
            return this.getXPos() - o.getXPos();
        }
        return this.getYPos() - o.getYPos();

    }




}


