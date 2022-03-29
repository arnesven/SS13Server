package model.plebOS.klondike;

import graphics.sprites.Sprite;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KlondikeDeck implements Serializable {
    private List<KlondikeCard> cards = new ArrayList<>();
    private Sprite sprite;

    public void drawYourself(Graphics2D g, int x, int y) {
        this.sprite = new Sprite("klondikedeck", "playing_cards.png", 17, 11, null);
        try {
            g.drawImage(sprite.getImage(), x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
