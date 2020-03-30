package clientview;

import java.awt.*;

public abstract class DrawingStrategy {
    private final MapPanel mapPanel;
    private BackgroundDrawingStrategy bgStrategy;


    public DrawingStrategy(MapPanel mapPanel, BackgroundDrawingStrategy bgStrat) {
        this.mapPanel = mapPanel;
        this.bgStrategy = bgStrat;
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

}
