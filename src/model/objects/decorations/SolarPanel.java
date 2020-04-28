package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class SolarPanel extends GameObject {
    private final int rotation;

    public SolarPanel(Room position) {
        super("Solar Panel", position);
        this.rotation = MyRandom.nextInt(8);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        List<Sprite> sprs = new ArrayList<>();

        int column = rotation;
        int row = 3;
        if (rotation == 7) {
            column = 12;
            row = 2;
        }
        sprs.add(new Sprite("solarpanel", "power.png", column, row, 32,32,this));
        return new Sprite("solarpanalbase" + rotation, "power.png", 8, 3, 32,32, sprs, this);
    }
}
