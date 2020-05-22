package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.floors.HallwayFloorSet;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class InnerWindow extends DecorationObject {
    public InnerWindow(Room position) {
        super("Inner Window", position);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        List<Sprite> sprs = new ArrayList<Sprite>();
        sprs.add(new HallwayFloorSet().getMainSprite());
        sprs.add(new Sprite("inwindow", "walls.png", 4, 21, this));
        return new Sprite("innerwindow", "human.png", 0, sprs, this);
    }
}
