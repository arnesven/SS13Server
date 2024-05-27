package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;

public class BananaPeelItem extends GameItem {
    public BananaPeelItem() {
        super("Banana Peel", 0.01, false, 0);
    }

    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("banana", "items.png", 63, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "The peel of a banana. Slippery.";
    }

    @Override
    public GameItem clone() {
        return new BananaPeelItem();
    }
}
