package clientview.strategies;

import clientview.MapPanel;

import java.awt.*;

public abstract class DrawingStrategy {
    private final MapPanel mapPanel;
    private BackgroundDrawingStrategy bgStrategy;
    private RoomDrawingStrategy roomStrategy;


    public DrawingStrategy(MapPanel mapPanel, BackgroundDrawingStrategy bgStrat, RoomDrawingStrategy roomStrat) {
        this.mapPanel = mapPanel;
        this.bgStrategy = bgStrat;
        this.roomStrategy = roomStrat;
    }

    public abstract void paint(Graphics g);

    public BackgroundDrawingStrategy getBackgroundDrawingStrategy() {
        return bgStrategy;
    }

    public void setBackgroundDrawingStrategy(BackgroundDrawingStrategy strat) {
        bgStrategy = strat;
    }

    public RoomDrawingStrategy getRoomDrawingStrategy() { return roomStrategy; }

    public void setRoomDrawingStrategy(RoomDrawingStrategy roomStrat) { roomStrategy = roomStrat; }

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

}
