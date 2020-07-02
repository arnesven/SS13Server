package clientview.strategies;

import clientlogic.GameData;
import clientlogic.Room;
import clientview.animation.AnimationHandler;
import clientview.components.MapPanel;
import graphics.ExtraEffect;
import shared.ClientExtraEffect;

import java.awt.*;

public abstract class DrawingStrategy {
    private final MapPanel mapPanel;
    private BackgroundDrawingStrategy bgStrategy;
    private int extraXOffset;
    private int extraYOffset;

    public DrawingStrategy(MapPanel mapPanel, BackgroundDrawingStrategy bgStrat) {
        this.mapPanel = mapPanel;
        this.bgStrategy = bgStrat;
        extraXOffset = 0;
        extraYOffset = 0;
    }

    public abstract void paint(Graphics g);

    public BackgroundDrawingStrategy getBackgroundDrawingStrategy() {
        return bgStrategy;
    }

    public void setBackgroundDrawingStrategy(BackgroundDrawingStrategy strat) {
        bgStrategy = strat;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    protected int inventoryPanelHeight() {
        return getMapPanel().getInventoryPanel().getHeight();
    }

    protected int getZoom() {
        return getMapPanel().getZoom();
    }

    protected int getWidth() {
        return getMapPanel().getWidth();
    }

    protected int getHeight() {
        return getMapPanel().getHeight();
    }

    protected int getXTrans() {
        return getMapPanel().getXTrans();
    }

    protected int getYTrans() {
        return getMapPanel().getYTrans();
    }

    protected RoomDrawingStrategy getRoomDrawingStrategy(Room r) {
        if (r.getRoomStyle().toLowerCase().equals("nowallsnodoors")) {
            return new NoWallsNoDoorsRoomStrategy();
        } else if (r.getRoomStyle().toLowerCase().equals("wallsnowindows")) {
            return new WallsNoWindowsRoomStrategy();
        } else if (r.getRoomStyle().toLowerCase().equals("dontpaint")) {
            return new DontPaintRoomStrategy();
        }
        return new WallsAndWindowsRoomStrategy();
    }


    protected int calculateXOffsetPx() {
        return getXTrans() + extraXOffset;
    }

    protected int calculateYOffsetPx() {
        return inventoryPanelHeight() + getYTrans() + extraYOffset;
    }

    public void setExtraXOffset(int i) {
        extraXOffset = i;
    }

    public void setExtraYOffset(int i) {
        extraYOffset = i;
    }

    public void applyCameraPanEffect() {
        for (ClientExtraEffect ee : GameData.getInstance().getExtraEffects()) {
            if (ee instanceof CameraPanEffect) {
                ((CameraPanEffect) ee).applyYourself(this);
            }
        }
    }
}
