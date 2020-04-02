package clientview.overlays;

import clientview.GraphicsUtils;
import clientview.components.MapPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class OverlayComponent {

    private Dimension size;
    private Color fillColor = GraphicsUtils.getDefaultBackgroundColor();
    private Color borderColor1 = GraphicsUtils.getThickStrokeColor();
    private Color borderColor2 = GraphicsUtils.getThinStrokeColor();
    private Stroke thickStroke = GraphicsUtils.getThickStroke();
    private Stroke thinStroke = GraphicsUtils.getThinStroke();


    public OverlayComponent(Dimension size) {
        this.size = size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void drawYourself(Graphics g, MapPanel comp) {
        drawYourBackground(g);
        drawYourFrame(g);
        Point position = getPosition();
        Graphics2D g2d = (Graphics2D)g;
        subDraw(g2d, position, comp);
    }

    public void drawYourBackground(Graphics g) {
        Point position = getPosition();
        int x = position.x;
        int y = position.y;

        Graphics2D g2d = (Graphics2D)g;
        Stroke oldstrok = g2d.getStroke();

        g.setColor(fillColor);
        g.fillRect(x, y, size.width, size.height);
        g2d.setStroke(oldstrok);

    }

    public void drawYourFrame(Graphics g) {
        Point position = getPosition();
        int x = position.x;
        int y = position.y;

        Graphics2D g2d = (Graphics2D)g;
        Stroke oldstrok = g2d.getStroke();
        g2d.setStroke(thickStroke);
        g.setColor(borderColor1);
        g.drawRect(x, y, size.width, size.height);

        g2d.setStroke(thinStroke);
        g.setColor(borderColor2);
        g.drawRect(x, y, size.width, size.height);

        g2d.setStroke(oldstrok);
    }

    protected abstract Point getPosition();


    public Dimension getSize() {
        return size;
    }


    public Color getBorderColor1() {
        return borderColor1;
    }

    public Color getBorderColor2() {
        return borderColor2;
    }


    public Stroke getThickStroke() {
        return thickStroke;
    }

    public Stroke getThinStroke() {
        return thinStroke;
    }


    protected void subDraw(Graphics2D g2d, Point position, MapPanel comp) {

    }

    public boolean handleMouseClick(MouseEvent e) {
        if (affectsThisComponent(e)) {
            this.onClick(e);
            return true;
        }
        return false;
    }

    public boolean handleMousePressed(MouseEvent e) {
        if (affectsThisComponent(e)) {
            this.onPressed(e);
            return true;
        }
        return false;
    }


    public boolean handleMouseReleased(MouseEvent e) {
        if (affectsThisComponent(e)) {
            this.onReleased(e);
            return true;
        }
        return false;
    }


    private boolean affectsThisComponent(MouseEvent e) {
        Rectangle rect = new Rectangle(getPosition(), size);
        return rect.contains(e.getPoint());
    }

    protected abstract void onClick(MouseEvent e);

    protected abstract void onPressed(MouseEvent e);

    protected abstract void onReleased(MouseEvent e);

    protected abstract void onMouseDragged(MouseEvent e);

    protected abstract void onMouseMove(MouseEvent e);

    public void setThickStroke(BasicStroke thickStroke) {
        this.thickStroke = thickStroke;
    }

    public void setThinStroke(BasicStroke thinStroke) {
        this.thinStroke = thinStroke;
    }

    public void swapColors() {
        Color c = borderColor1;
        borderColor1 = fillColor;
        fillColor = c;

    }

    public boolean handleMouseMove(MouseEvent e) {
        if (affectsThisComponent(e)) {
            this.onMouseMove(e);
            return true;
        }
        return false;
    }


    public void drawYourself(Graphics g) {
        drawYourself(g, null);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public boolean handleKeyboard(KeyEvent e) {
        return false;
    }

    public boolean handleMouseDragged(MouseEvent e) {
        if (affectsThisComponent(e)) {
            this.onMouseDragged(e);
            return true;
        }
        return false;
    }


    public abstract void returnWasHit();

}
