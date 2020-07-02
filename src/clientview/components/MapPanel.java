package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientlogic.Room;
import clientview.animation.AnimationHandler;
import clientview.strategies.BackgroundDrawingStrategy;
import clientview.strategies.DrawingStrategy;
import clientview.strategies.StationDrawingStrategy;
import shared.ClientExtraEffect;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel implements Observer {

    private static int xTrans;
    private static int yTrans;
    private static int zTrans;

    private static boolean automaticBackground = true;
    private static int zoom = 32;

    private final InventoryPanel inventoryPanel;
    private final DrawingStrategy drawingStrategy;

    private GameUIPanel parent;
    private int oldState;

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



    public void createRooms() {
        this.oldState = GameData.getInstance().getDataState();
        ServerCommunicator.send(parent.getUsername() + " MAP VIMI " + getWidth() + " " +
                        (getHeight()-inventoryPanel.getHeight()),
                new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                String[] parts = result.split("<vimi>");
                GameData.getInstance().deconstructRoomList(parts[0], GameData.getInstance().getRooms());
                GameData.getInstance().deconstructRoomList(parts[1], GameData.getInstance().getMiniMap());
                parent.repaint();
            }

                    @Override
                    public void onFail() {
                        System.out.println("Failed to send MAP VIMI message to server");
                    }

                });
    }

    private void drawRooms() {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
       // System.out.println("Painting!");
        //long dt = System.currentTimeMillis();
        AnimationHandler.step();
        if (automaticBackground) {
            checkBackgroundStrategy();
        }
        drawingStrategy.applyCameraPanEffect();
        drawingStrategy.paint(g);
        for (ClientExtraEffect ee : GameData.getInstance().getExtraEffects()) {
            ee.drawYourself(g);
        }
        inventoryPanel.drawYourself(g, 0, getWidth());
        //dt = System.currentTimeMillis() - dt;
        //System.out.println("Took " + dt + "ms to paint result");
    }

    private void checkBackgroundStrategy() {
        for (Room r : GameData.getInstance().getRooms()) {
            if (r.getID() == GameData.getInstance().getCurrentPos()) {
                if (!drawingStrategy.getBackgroundDrawingStrategy().getName().equals(r.getBackgroundType())) {
                    System.out.println("Changing background to..." + r.getBackgroundType());
                    drawingStrategy.setBackgroundDrawingStrategy(BackgroundDrawingStrategy.makeStrategy(r.getBackgroundType()));
                    break;
                }
            }
        }
    }

    public static int getZoom() {
        return zoom;
    }

    public static void setZoom(int i) {
        zoom = i;
    }

    @Override
    public void update() {

        if (GameData.getInstance().getDataState() > this.oldState) {
            createRooms();
        }
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

    @Override
    public void unregisterYourself() {
        GameData.getInstance().unsubscribe(this);
    }
}
