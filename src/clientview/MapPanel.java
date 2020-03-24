package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientlogic.Room;
import clientview.overlays.OverlayButton;
import clientview.overlays.TitledOverlayComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapPanel extends JPanel implements Observer {

    public static final int WIDTH = 350;
    private static final int MAP_REFRESH_DELAY = 5000;
    private static int xTrans;
    private static int yTrans;

    private final Timer timer;
    private final InventoryPanel inventoryPanel;

    private GameUIPanel parent;
    private int xOffset;
    private int yOffset;
    private ArrayList<ImageIcon> spaceSprites;
    private TitledOverlayComponent toc;

    public MapPanel(GameUIPanel parent) {
        this.parent = parent;

        timer = new Timer(MAP_REFRESH_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRooms();
            }
        });
        timer.start();
        this.inventoryPanel = new InventoryPanel();
        createRooms();
        createSpaceSprites();
        //this.add(new JLabel("MapPanel"));
        GameData.getInstance().subscribe(this);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (inventoryPanel.mouseClicked(e, MapPanel.this)) {
                    return;
                }

                if (toc != null) {
                    if (toc.handleMouseClick(e)) {
                        return;
                    }
                }

                MyPopupMenu mpm = null;
                for (OverlaySprite ps : GameData.getInstance().getOverlaySprites()) {
                    if (ps.mouseHitsThis(e)) {
                        if (mpm == null) {
                            mpm = ps.getPopupMenu(e);
                        } else {
                            mpm.addAll(ps.getPopupMenu(e));
                        }

                    }
                }
                if (mpm != null) {
                    mpm.showYourself();
                    return;
                }

                List<Room> l = new ArrayList<Room>();
                l.addAll(GameData.getInstance().getMiniMap());
                for (Room r : l) {
                        if (r.actOnClick(e)) {
                            return;
                        }
                }

            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (toc != null) {
                    if (toc.handleMouseMove(e)) {
                        return;
                    }
                }

                  List<Room> l = new ArrayList<Room>();
                l.addAll(GameData.getInstance().getMiniMap());
                for (Room r : l) {
                    r.isHoveredOver(e, MapPanel.this);
                }
                for (OverlaySprite os : GameData.getInstance().getOverlaySprites()) {
                    os.isHoveredOver(e, MapPanel.this);
                }
                if (inventoryPanel != null) {
                    inventoryPanel.mouseHover(e, MapPanel.this);
                }

            }
        });


       // addAPopup();


//
    }

    public static void addXTranslation(int i) {
        xTrans += i;
    }

    public static void addYTranslation(int i) {
        yTrans += i;
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


    private void createRooms() {
        ServerCommunicator.send(parent.getUsername() + " MAP VISI " + getWidth() + " " +
                        (getHeight()-inventoryPanel.getHeight()),
                new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                GameData.getInstance().deconstructRoomList(result, GameData.getInstance().getRooms());
                //	Window.alert(Window.getClientWidth() + "");
                drawRooms();
            }

            @Override
            public void onFail() {
                timer.stop();
            }
            });

        ServerCommunicator.send(parent.getUsername() + " MAP MINI " + + getWidth() + " " +
                        (getHeight()-inventoryPanel.getHeight()),
                new MyCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        GameData.getInstance().deconstructRoomList(result, GameData.getInstance().getMiniMap());
                        //	Window.alert(Window.getClientWidth() + "");
                        parent.repaint();
                    }

                    @Override
                    public void onFail() {
                        timer.stop();
                    }
                });

    }

    private void drawRooms() {
        this.repaint();
     //   this.removeAll();

        // TODO

    }


    @Override
    protected void paintComponent(Graphics g) {

        int zoom = getZoom();
        if (Room.isAutomaticScaling()) {
            int mapWidth = GameData.getInstance().getMapWidth();
            int xscale = (getWidth() / (mapWidth*zoom)) * zoom;
            int mapHeight = GameData.getInstance().getMapHeight();
            int yscale = ((getHeight() - inventoryPanel.getHeight()) / (mapHeight*zoom))*zoom;

            Room.setXScale((double) xscale);
            Room.setYScale((double) yscale);
        }
       // System.out.println("Scale is " + Room.getXScale() + " " + Room.getYScale());
        xOffset = GameData.getInstance().getMinX() + xTrans;
        yOffset = GameData.getInstance().getMinY() + yTrans;

        drawSpace(g);
      //  System.out.println("Room scale: X:" + Room.getXScale() +", Y:" + Room.getYScale());

        List<Room> roomList = new ArrayList<>();
        roomList.addAll(GameData.getInstance().getMiniMap());
        Collections.sort(roomList);


        for (Room r : roomList) {
                boolean shadow = !GameData.getInstance().isASelectableRoom(r.getID());
                r.drawYourself(g, GameData.getInstance().getSelectableRooms().contains(r.getID()),
                        GameData.getInstance().getCurrentPos() == r.getID(),
                        xOffset, yOffset, 0, inventoryPanel.getHeight(), shadow);

        }



        for (Room r : roomList) {
            // r.addYourDoors(this, xOffset, yOffset);
            if (GameData.getInstance().isASelectableRoom(r.getID())) {
                r.drawYourDoors(g, this, xOffset, yOffset, 0, inventoryPanel.getHeight());
            }
            //r.addDecorations();
        }


        for (OverlaySprite sp : GameData.getInstance().getOverlaySprites()) {
            sp.drawYourself(g, this, xOffset, yOffset, 0, inventoryPanel.getHeight());
        }

        for (Room r : roomList) {
            r.drawYourFrame(g, xOffset, yOffset, 0, inventoryPanel.getHeight(),
                    GameData.getInstance().getCurrentPos() == r.getID());
        }


        inventoryPanel.drawYourself(g, 0, getWidth());

        if (toc != null) {
            toc.drawYourself(g);
        }

    }

    public static int getZoom() {
        return 32;
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


    @Override
    public void update() {
            createRooms();
    }

    private void addAPopup() {
        this.toc = new TitledOverlayComponent(new Dimension(200, 133), "My Test",
                true, this, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toc = null;
                System.out.println("Disposed");
                repaint();
            }
        });

        this.toc.addText("This is a test of a popup.");
        this.toc.addButton(new OverlayButton("OK", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toc = null;
                repaint();
            }
        }));

    }
}
