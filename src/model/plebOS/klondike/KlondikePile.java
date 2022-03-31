package model.plebOS.klondike;

import model.plebOS.KlondikeCommand;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class KlondikePile extends ArrayList<KlondikeCard> {
    public static final int ROW_HEIGHT = 8;
    private final boolean revelead;
    private final int xpos;
    private final int ypos;

    public KlondikePile(boolean drawLong, int xpos, int ypos) {
        this.revelead = drawLong;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public void drawYourself(Graphics2D g) {
        if (isEmpty()) {
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
    }

    protected Rectangle getHitBox(int cardIndex) {
        if (revelead) {
            return new Rectangle(xpos + 21, ypos + 7 + (cardIndex+1) * ROW_HEIGHT, 24, 34);
        }
        return new Rectangle(xpos + 21, ypos + 7 + ROW_HEIGHT, 24, 34);
    }

    protected Rectangle getHitBox() {
        return getHitBox(this.size()-1);
    }

    protected boolean isClicked(int x, int y) {
        return getHitBox().contains(new Point(x, y));
    }

    public KlondikeCard top() {
        if (isEmpty()) {
            return null;
        }
        return this.get(this.size()-1);
    }

    public KlondikeCard pop() {
        if (isEmpty()) {
            return null;
        }
        KlondikeCard c = top();
        remove(c);
        return c;
    }

    public void flipAll() {
        for (KlondikeCard c : this) {
            c.flip();
        }
    }

    public boolean handleClick(KlondikeCommand klondikeCommand, int x, int y) {
        if (isClicked(x, y)) {
            klondikeCommand.selectCard(top(), getHitBox());
            return true;
        }
        return false;
    }
}
