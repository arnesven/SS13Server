package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;

public class BloodPool extends BloodyMess {
    public BloodPool(Room position) {
        super(position);
        setName("Pool of Blood");
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("poolofblood", "blood.png", 0, 6, this);
    }
}
