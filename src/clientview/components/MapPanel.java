package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.ClientDoor;
import clientlogic.GameData;
import clientlogic.Observer;
import clientlogic.Room;
import clientview.OverlaySprite;
import clientview.animation.AnimationHandler;
import clientview.strategies.BackgroundDrawingStrategy;
import clientview.strategies.DrawingStrategy;
import clientview.strategies.StationDrawingStrategy;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MapPanel extends JPanel implements Observer {

    private static int xTrans;
    private static int yTrans;
    private static int zTrans;

    private static boolean automaticBackground = true;

    private final InventoryPanel inventoryPanel;
    private final DrawingStrategy drawingStrategy;

    private GameUIPanel parent;

    public MapPanel(GameUIPanel parent) {
        this.parent = parent;
        this.drawingStrategy = new StationDrawingStrategy(this);

        this.inventoryPanel = new InventoryPanel();
        createRooms();
        GameData.getInstance().subscribe(this);

        MapPanelMouseListener mpml = new MapPanelMouseListener(this);
        this.addMouseListener(mpml);
        this.addMouseMotionListener(mpml);
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

    public static void setXTranslation(int i) {
        xTrans = i;
    }

    public static void setYTranslation(int i) {
        yTrans = i;
    }

    public static void setAutomaticBackground(boolean b) {
        automaticBackground = b;
    }


    private void createRooms() {
        ServerCommunicator.send(parent.getUsername() + " MAP VISI " + getWidth() + " " +
                        (getHeight()-inventoryPanel.getHeight()),
                new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                GameData.getInstance().deconstructRoomList(result, GameData.getInstance().getRooms());
                drawRooms();
            }

            });

        ServerCommunicator.send(parent.getUsername() + " MAP MINI " + + getWidth() + " " +
                        (getHeight()-inventoryPanel.getHeight()),
                new MyCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        GameData.getInstance().deconstructRoomList(result, GameData.getInstance().getMiniMap());
                        parent.repaint();
                    }

                });

    }

    private void drawRooms() {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        AnimationHandler.step();
        if (automaticBackground) {
            checkBackgroundStrategy();
        }
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
