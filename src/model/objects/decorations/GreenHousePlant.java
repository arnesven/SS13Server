package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class GreenHousePlant extends GameObject {
    public GreenHousePlant(Room position) {
        super("Palm Tree", position);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("greenhouseplant", "hydroponics.png", 1, 9, this);
    }
}
