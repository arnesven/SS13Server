package clientlogic;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientview.MapPanel;
import clientview.MyLabel;
import clientview.SpriteManager;
import util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

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

    private String shortname;

    //private Color originalBackgroundColor;


    public Room(int ID, String name, String shortname, int x, int y, int width, int height,
                double[] doors, String color) {
        this.ID = ID;
        this.name = name;
        this.shortname = shortname;
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
            for (int row = startSpritePaint; row < height * (yscale / background.getIconHeight()) + 1; ++row) {
                for (int col = startSpritePaint; col < width * (xscale / background.getIconWidth()) + 1; ++col) {
                    ImageIcon imgToDraw = background;
                    imgToDraw = getFloorForPos(row == startSpritePaint, row == (height * (yscale / background.getIconHeight())),
                                               col == startSpritePaint, col == width * (xscale / background.getIconWidth()),
                                               background);

                    g.drawImage(imgToDraw.getImage(),
                            x + col * imgToDraw.getIconWidth(),
                            y + row * imgToDraw.getIconHeight(), null);
                }
            }
        } else {
            g.setColor(Color.BLACK);

            g.fillRect(x + background.getIconWidth(), y + background.getIconHeight(),
                finalW, finalH);
        }


    }

    private int getScaledHeightPX() {
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

//        g.setColor(WALL_COLOR);

        if (!shadow) {
            g.setColor(backgroundColor);
        } else {
            g.setColor(Color.BLACK);
        }

        g.fillPolygon(new int[]{x, x + background.getIconWidth(), x + background.getIconWidth()},
                new int[]{y + finalH, y + finalH, y + finalH + background.getIconHeight()}, 3);
        g.fillPolygon(new int[]{x + finalW, x + finalW + background.getIconWidth(), x + finalW},
                new int[]{y, y + background.getIconHeight(), y + background.getIconHeight()}, 3);
        g.setColor(Color.BLACK);
        g.drawLine(x + finalW, y + finalH,
                x + finalW + background.getIconWidth(), y + finalH + background.getIconHeight());
        g.drawLine(x, y + finalH, x + background.getIconHeight(), y + finalH + background.getIconHeight());
        g.drawLine(x + finalW, y, x + finalW + background.getIconWidth(), y + background.getIconHeight());

        if (!shadow) {
            g.setColor(backgroundColor);

        }
        g.fillRect(x, y, finalW, finalH);

        g.setColor(WALL_COLOR);

        g.drawLine(x, y, x + background.getIconWidth(), y + background.getIconHeight());
        g.drawLine(x, y+finalH, x+background.getIconWidth(), y+finalH+background.getIconWidth());
        g.drawLine(x+finalW, y, x+finalW+background.getIconWidth(), y+background.getIconHeight());

        decorateWallsWithWindows(g, x, y, finalW, finalH, background, shadow);
    }

    private void decorateWallsWithWindows(Graphics g, int x, int y, int finalW, int finalH, ImageIcon background, boolean shadow) {
        ImageIcon left = SpriteManager.getSprite("skewedwindowleft0");
        ImageIcon right = SpriteManager.getSprite("skewedwindowright0");
        ImageIcon top = SpriteManager.getSprite("skewedwindowtop0");
        ImageIcon bottom = SpriteManager.getSprite("skewedwindowbottom0");

      //  for (int xpos = x + left.getIconWidth()/5; xpos < finalW; xpos += left.getIconWidth()*2) {
        if (!shadow) {
            for (int i = 0; i < (finalW / (left.getIconWidth() * 2)); ++i) {
                int xposleft = 15 + x + left.getIconWidth() * i * 2;
                int xposright = 15 + x + left.getIconWidth() * (i * 2 + 1);

                if ((i == 0 && isBothSuitableForWindow(i)) || (i > 0 && isTopSuitableForWindow(i))) {
                    g.drawImage(left.getImage(), xposleft, y, null);
                    g.drawImage(right.getImage(), xposright, y, null);
                }
            }

            for (int i = 0; i < (finalH / (left.getIconHeight() * 2)); ++i) {
                int ypostop = 10 + y + left.getIconHeight() * i * 2;
                int yposbot = 10 + y + left.getIconHeight() * (i * 2 + 1);

                if ((i == 0 && isBothSuitableForWindow(i)) || (i > 0 && isLeftSuitableForWindow(i))) {
                    g.drawImage(top.getImage(), x, ypostop, null);
                    g.drawImage(bottom.getImage(), x, yposbot, null);
                }
            }


        }
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
                if (isTopBottomDoor(doors[i], doors[i+1])) {
                    ic = SpriteManager.getSprite("skeweddoorleft0");
                    ic2 = SpriteManager.getSprite("skeweddoorright0");
                } else {
                    ic = SpriteManager.getSprite("skeweddoortop0");
                    ic2 = SpriteManager.getSprite("skeweddoorbottom0");
                }

                int xpos;
                if (doors[i] < 0.0) { // dummy door (for locked doors)
                    xpos = (int)((-doors[i]-xOffset) * getXScale()) + xOffPx;
                } else {
                    xpos = (int)((doors[i]-xOffset) * getXScale()) + xOffPx;
                }

                int ypos = (int)((doors[i+1]-yOffset) * getYScale()) + yOffPx;
                //g.setColor(doorColor);
                //g.fillRect(xpos, ypos, width, height);


                if (isTopBottomDoor(doors[i], doors[i+1])) {
                    g.drawImage(ic.getImage(), xpos - ic2.getIconWidth() / 2, ypos, null);
                    g.drawImage(ic2.getImage(), xpos + ic2.getIconWidth() / 2, ypos, null);
                } else {
                    g.drawImage(ic.getImage(), xpos, ypos-ic2.getIconHeight() / 2, null);
                    g.drawImage(ic2.getImage(), xpos, ypos+ic2.getIconHeight() / 2, null);
                }
                g.setColor(Color.BLACK);
                //g.drawRect(xpos, ypos, width, height);
            }
    }

    public void drawYourFrame(Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, boolean selected) {
        int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
        int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
        int finalW = (int) (getWidth() * getXScale());
        int finalH = (int) (getHeight() * getYScale());
        if (selected) {
            ((Graphics2D)g).setStroke(new BasicStroke(4));
            g.setColor(SELECTED_ROOM_COLOR);
        } else {
            g.setColor(WALL_COLOR);
        }
        if (!floorSpriteBaseName.contains("outdoor")) {
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
        return this.getDistance() - o.getDistance();

    }

    private int getDistance() {
        return this.xPos*this.xPos + this.yPos*this.yPos;
    }



}


