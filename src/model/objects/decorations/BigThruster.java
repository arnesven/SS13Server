package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.EscapeShuttle;

public class BigThruster extends ShuttleThruster {
    public BigThruster(EscapeShuttle escapeShuttle, double v) {
        super(escapeShuttle, 0.3, v);
    }


    @Override
    protected Sprite getDownSprite() {
        return new Sprite("bigthrusterdown", "bigthrusters.png", 1, 0, 64, 64, this);
    }

    @Override
    protected Sprite getUpSprite() {
        return new Sprite("bigthrusterup", "bigthrusters.png", 0, 0, 64,64, this);
    }

    @Override
    protected Sprite getLeftSprite() {
        return new Sprite("bigthrusterleft", "bigthrusters.png", 2, 0,  64, 64, this);
    }

    @Override
    protected Sprite getRightSprite() {
        return new Sprite("bigthrusterright", "bigthrusters.png", 3, 0, 64, 64, this);
    }


}
