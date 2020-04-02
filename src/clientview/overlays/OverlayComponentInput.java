package clientview.overlays;


import clientview.GraphicsUtils;
import clientview.components.MapPanel;
import clientview.animation.AnimationHandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class OverlayComponentInput extends OverlayContent {
    private final String name;
    private final TitledOverlayComponent parent;
    private final boolean drawWithLabel;
    private int width = 100;
    private boolean isSelected = false;
    private StringBuilder currentContent = new StringBuilder();
    private Rectangle rect;

    public OverlayComponentInput(TitledOverlayComponent titledOverlayComponent, String name, int width, boolean drawWithLabel) {
        this.name = name;
        this.parent = titledOverlayComponent;
        this.width = width;
        this.drawWithLabel = drawWithLabel;
    }

    @Override
    public int drawYourself(Graphics2D g2d, int i, int y, MapPanel comp) {

        g2d.setColor(GraphicsUtils.getDefaultTextBackground());
        y += 4;
        int extra = 0;
        if (drawWithLabel) {
            extra = g2d.getFontMetrics().stringWidth(name) + 4;
            int labelX =  i + (int)(parent.getSize().getWidth() - (width + extra)) / 2;
            g2d.drawString(name, labelX, y+g2d.getFontMetrics().getHeight());
        }

        int x = i + (int)(parent.getSize().getWidth() - (width + extra)) / 2;
        rect = new Rectangle(x+extra, y, width+4, g2d.getFontMetrics().getHeight() + 4);
        g2d.fillRect(x+extra, y, width+4, g2d.getFontMetrics().getHeight() + 4);
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x+extra, y, width+3, g2d.getFontMetrics().getHeight() + 3);
        g2d.setColor(GraphicsUtils.getDefaultTextForeground());

        int start = 0;
        while (g2d.getFontMetrics().stringWidth(currentContent.substring(start)) > width) {
            start++;
        }
        g2d.drawString(currentContent.substring(start), x+extra+1, y+g2d.getFontMetrics().getHeight()-2);

        if (isSelected) {
            int caretX = x + extra + 1;
            caretX += g2d.getFontMetrics().stringWidth(currentContent.substring(start).toString());
            if ((AnimationHandler.getState() / 8) % 2 == 0) {
                g2d.drawLine(caretX, y + 3, caretX, y + g2d.getFontMetrics().getHeight() - 1);
            }
        }


        return g2d.getFontMetrics().getHeight() + 8;
    }

    public void handleMouseClick(MouseEvent e) {
        if (rect != null) {
            if (rect.contains(e.getPoint())) {
                this.isSelected = true;
            }
        }
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void handleKeyboard(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (currentContent.length() > 0) {
                currentContent.deleteCharAt(currentContent.length() - 1);
            }
        } else if (keyCharIsOk(e.getKeyChar())) {
            currentContent.append(e.getKeyChar());
        }
    }

    protected boolean keyCharIsOk(char keyChar) {
        return keyChar >= ' ' && keyChar <= 'z';
    }

    public String getInput() {
        return currentContent.toString();
    }

    public void setInput(String input) {
        this.currentContent = new StringBuilder(input);
    }

    public String getName() {
        return name;
    }
}
