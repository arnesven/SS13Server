package model.plebOS.klondike;

import graphics.sprites.Sprite;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KlondikeDeck implements Serializable {
    private final Rectangle hitBox;
    private final int xoff;
    private final int yoff;
    private List<KlondikeCard> cards = new ArrayList<>();
    public static Sprite sprite = new Sprite("klondikedeck", "playing_cards.png", 4, 3, null);

    public KlondikeDeck(int marginX, int marginY) {
        this.xoff = marginX;
        this.yoff = marginY;
        this.hitBox = new Rectangle(marginX+21, marginY+13, 24, 34);
        int column = 7;
        int row = 3;
        int suit = 0;
        int value = 1;
        for (int i = 0; i < 52; ++i) {
            cards.add(new KlondikeCard(value, numToSuit(suit), column, row));
            column += 3;
            suit++;
            if (column > 18) {
                column = column - 19;
                row++;
            }
            if (suit > 3) {
                suit = suit - 4;
                value++;
            }
        }
        Collections.shuffle(cards);
    }

    private static String numToSuit(int i) {
        if (i == 0) {
            return "c";
        } else if (i == 1) {
            return "d";
        } else if (i == 2) {
            return "h";
        }
        return "s";
    }

    public void drawYourself(Graphics2D g) {
        if (cards.isEmpty()) {
            return;
        }
        try {
            g.drawImage(sprite.getImage(), xoff, yoff, 64, 64, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setColor(Color.PINK);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public KlondikeCard pop() {
        KlondikeCard c = cards.get(0);
        cards.remove(0);
        return c;
    }

    public boolean isClicked(int x, int y) {
        return hitBox.contains(new Point(x, y));
    }
}
