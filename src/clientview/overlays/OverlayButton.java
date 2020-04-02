package clientview.overlays;

import clientview.components.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OverlayButton extends OverlayComponent {
    private String name;
    private Point position;
    private Color textColor = Color.BLACK;
    private ActionListener al;

    public OverlayButton(String title) {
       this(title, null);
    }

    public OverlayButton(String title, ActionListener actionListener) {
        super(new Dimension(80, 26));
        this.name = title;
        position =  new Point(0, 0);
        this.setThickStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        this.setThinStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        al = actionListener;
    }

    @Override
    protected Point getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void subDraw(Graphics2D g2d, Point position, MapPanel comp) {
        g2d.setColor(textColor);
        g2d.drawString(name, position.x + (int)((getSize().width - g2d.getFontMetrics().stringWidth(name)) / 2),
                position.y + (int)(g2d.getFontMetrics().getHeight()) + 2) ;
    }

    @Override
    protected void onClick(MouseEvent e) {
        if (al != null) {
            al.actionPerformed(new ActionEvent(e, 0, "asdf"));
        }
    }

    @Override
    protected void onPressed(MouseEvent e) {
        super.swapColors();
    }

    @Override
    protected void onReleased(MouseEvent e) {
        super.swapColors();
    }

    @Override
    protected void onMouseDragged(MouseEvent e) {

    }

    @Override
    protected void onMouseMove(MouseEvent e) {

    }

    @Override
    public void returnWasHit() {
        al.actionPerformed(new ActionEvent(new JPanel(), 0, "asdf"));
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public MouseListener createStandardMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseReleased(e);
            }
        };
    }
}
