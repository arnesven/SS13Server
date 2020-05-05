package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.ShuttleRoom;

public class ShuttleChair extends ShuttleDecoration {
    public ShuttleChair(ShuttleRoom shuttleRoom) {
        super("Chair", shuttleRoom);
    }

    @Override
    protected Sprite getDownSprite() {
        return new Sprite("shuttlechairdown", "furniture2.png", 11, 5, this);
    }

    @Override
    protected Sprite getUpSprite() {
        return new Sprite("shuttlechairup", "furniture2.png", 0, 6, this);
    }

    @Override
    protected Sprite getLeftSprite() {
        return new Sprite("shuttlechairleft", "furniture2.png", 2, 6, this);
    }

    @Override
    protected Sprite getRightSprite() {
        return new Sprite("shuttlechairright", "furniture2.png", 1, 6, this);
    }

    @Override
    public boolean alwaysShowSprite() {
        return false;
    }


    @Override
    public void moveTo(int x, int y, int z) {

    }
}
