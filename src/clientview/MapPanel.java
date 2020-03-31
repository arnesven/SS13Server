package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientlogic.Room;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MapPanel extends JPanel implements Observer {

    public static final int WIDTH = 350;
    private static final int MAP_REFRESH_DELAY = 5000;
    private static int xTrans;
    private static int yTrans;
    private static int zTrans;

    private final Timer timer;
    private final InventoryPanel inventoryPanel;
    private final DrawingStrategy drawingStrategy;

    private GameUIPanel parent;

    public MapPanel(GameUIPanel parent) {
        this.parent = parent;
        this.drawingStrategy = new StationDrawingStrategy(this);

        timer = new Timer(MAP_REFRESH_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRooms();
            }
        });
        timer.start();
        this.inventoryPanel = new InventoryPanel();
        createRooms();
        GameData.getInstance().subscribe(this);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (inventoryPanel.mouseClicked(e, MapPanel.this)) {
                    return;
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

    }

    public static void addXTranslation(int i) {
        xTrans += i;
    }

    public static void addYTranslation(int i) {
        yTrans += i;
    }

    public static void addZTranslation(int i) {
        zTrans += i;
    }

    public static int getZTranslation() {
        return zTrans;
    }

    public static void setZTranslation(int i) {
        zTrans = i;
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
    }

    @Override
    protected void paintComponent(Graphics g) {
       checkBackgroundStrategy();
       drawingStrategy.paint(g);
       inventoryPanel.drawYourself(g, 0, getWidth());
    }

    private void checkBackgroundStrategy() {
        for (Room r : GameData.getInstance().getRooms()) {
            if (r.getID() == GameData.getInstance().getCurrentPos()) {
                if (!drawingStrategy.getBackgroundDrawingStrategy().getName().equals(r.getBackgroundType())) {
                    System.out.println("Changing background to...");
                    drawingStrategy.setBackgroundDrawingStrategy(BackgroundDrawingStrategy.makeStrategy(r.getBackgroundType()));
                    break;
                }
            }
        }
    }

    public static int getZoom() {
        return 32;
    }

    @Override
    public void update() {
            createRooms();
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public int getXTrans() {
        return xTrans;
    }

    public int getYTrans() {
        return yTrans;
    }

    public DrawingStrategy getDrawingStrategy() {
        return drawingStrategy;
    }
}
