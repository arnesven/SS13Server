package model.plebOS.klondike;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

public class KlondikeCard implements Serializable {
    private final int value;
    private final String suit;
    private final Sprite sprite;

    public KlondikeCard(int value, String suit, int spriteColumn, int spriteRow) {
        this.value = value;
        this.suit = suit;
        this.sprite = new Sprite("kcard" + value + suit, "playing_cards.png", spriteColumn, spriteRow, null);
    }

    public void drawYourself(Graphics2D g, int x, int y) {
        try {
            g.drawImage(sprite.getImage(), x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
