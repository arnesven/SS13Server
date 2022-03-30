package model.plebOS.klondike;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import util.Logger;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

public class KlondikeCard implements Serializable {
    private final int value;
    private final String suit;
    private final Sprite sprite;
    private boolean revealed;

    public KlondikeCard(int value, String suit, int spriteColumn, int spriteRow) {
        this.value = value;
        this.suit = suit;
        this.sprite = new Sprite("kcard" + value + suit, "playing_cards.png", spriteColumn, spriteRow, null);
        this.revealed = false;
        Logger.log("KLONDIKE: Making card " + getName());
    }

    public String getName() {
        String value = this.value + "";
        if (this.value == 1) {
            value = "A";
        } else if (this.value == 11) {
            value = "J";
        } else if (this.value == 12) {
            value = "Q";
        } else if (this.value == 13) {
            value = "K";
        }
        return value + suit;
    }

    public void flip() {
        this.revealed = !this.revealed;
    }

    public void drawYourself(Graphics2D g, int x, int y) {
        try {
            if (revealed) {
                g.drawImage(sprite.getImage(), x, y, 64, 64, null);
            } else {
                g.drawImage(KlondikeDeck.sprite.getImage(), x, y, 64, 64, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
