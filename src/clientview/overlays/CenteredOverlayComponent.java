package clientview.overlays;

import clientview.components.MapPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CenteredOverlayComponent extends OverlayComponent {
    private MapPanel comp;

    public CenteredOverlayComponent(Dimension size, MapPanel comp) {
        super(size);
        this.comp = comp;
    }

    @Override
    protected Point getPosition() {
        if (comp != null) {
            return new Point((comp.getWidth() - getSize().width) / 2,
                    (comp.getHeight() - getSize().height) / 2);
        }
        return new Point(0, 0);
    }

    @Override
    public void drawYourself(Graphics g, MapPanel comp) {
        if (this.comp == null) {
            this.comp = comp;
        }

        super.drawYourself(g, comp);
    }

    @Override
    protected void onClick(MouseEvent e) {

    }

    @Override
    protected void onPressed(MouseEvent e) {

    }

    @Override
    protected void onReleased(MouseEvent e) {

    }

    @Override
    protected void onMouseDragged(MouseEvent e) {

    }

    @Override
    protected void onMouseMove(MouseEvent e) {

    }

    @Override
    public void returnWasHit() {

    }
}
