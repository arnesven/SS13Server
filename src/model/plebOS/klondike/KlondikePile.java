package model.plebOS.klondike;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class KlondikePile extends ArrayList<KlondikeCard> {
    public static final int ROW_HEIGHT = 8;
    private final boolean revelead;
    private final int xpos;
    private final int ypos;

    public KlondikePile(boolean revealed, int xpos, int ypos) {
        this.revelead = revealed;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public void drawYourself(Graphics2D g) {
        if (isEmpty()) {
            Rectangle r = getHitBox();
            g.drawRect(r.x, r.y, r.width, r.height);
            return;
        }
        if (revelead) {
            int y = ypos;
            for (KlondikeCard c : this) {
                c.drawYourself(g, xpos, y);
                y += ROW_HEIGHT;
            }
        } else {
            this.get(this.size()-1).drawYourself(g, xpos, ypos);
        }
        g.setColor(Color.PINK);
        Rectangle r = getHitBox();
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    public Rectangle getHitBox() {
        int size = this.size();
        if (size == 0) {
            size = 1;
        }
        if (revelead) {
            return new Rectangle(xpos + 21, ypos + 5 + size * ROW_HEIGHT, 24, 34);
        }
        return new Rectangle(xpos + 21, ypos + 5 + 1 * ROW_HEIGHT, 24, 34);
    }

    public boolean isClicked(int x, int y) {
        return getHitBox().contains(new Point(x, y));
    }

    public KlondikeCard top() {
        if (isEmpty()) {
            return null;
        }
        return this.get(this.size()-1);
    }

}
