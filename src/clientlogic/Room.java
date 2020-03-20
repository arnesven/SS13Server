package clientlogic;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientview.MapPanel;
import clientview.MyLabel;
import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class Room extends MouseInteractable implements Comparable<Room> {

    public static final Color WALL_COLOR = new Color(0x303030);
    private static final Color SELECTED_ROOM_COLOR = Color.YELLOW;
    private static double DOOR_SIZE = 0.5;
    private final String floorSpriteName;

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
        floorSpriteName = color;
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

        ImageIcon background = SpriteManager.getSprite(floorSpriteName);

        //System.out.println("Drawing room " + getName() + " at " + getXPos() + "," + getYPos() + " wh=" + getWidth() +"x" + getHeight());
        int x = (int) ((xPos - xOffset) * getXScale()) + xOffPx;
        int y = (int) ((yPos - yOffset) * getYScale()) + yOffPx;
        int finalW = (int) (getWidth() * getXScale());
        int finalH = (int) (getHeight() * getYScale());

        super.setHitBox(x, y, finalW, finalH);

        int startSpritePaint = 0;
        if (!floorSpriteName.contains("outdoor")) {
            startSpritePaint = 1;
            drawWalls(g, x, y, finalW, finalH, background, shadow);
        }

        if (!shadow) {
            for (int row = startSpritePaint; row < height * (yscale / background.getIconHeight()) + 1; ++row) {
                for (int col = startSpritePaint; col < width * (xscale / background.getIconWidth()) + 1; ++col) {
                    g.drawImage(background.getImage(),
                            x + col * background.getIconWidth(),
                            y + row * background.getIconHeight(), null);
                }
            }
        } else {
            g.setColor(Color.BLACK);

            g.fillRect(x + background.getIconWidth(), y + background.getIconHeight(),
                finalW, finalH);
        }


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

                int xpos = (int)((doors[i]-xOffset) * getXScale()) + xOffPx;
                if (xpos < 0) {
                    xpos = -xpos;
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
        g.drawRect(x, y, finalW, finalH);

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
            xscale = d;
        }

        public static void setYScale(double d) {
            yscale = d;
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


