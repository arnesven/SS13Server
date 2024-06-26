package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class PaperItem extends GameItem {
    public PaperItem() {
        super("Piece of Paper", 0.0001, false, 0);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("pieceofpaper", "items.png", 38, this);
    }

    @Override
    public GameItem clone() {
        return new PaperItem();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A blank sheet. Good for writing on, or making paper airplanes.";
    }
}
