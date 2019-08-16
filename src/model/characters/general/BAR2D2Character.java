package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;

/**
 * Created by erini02 on 15/11/16.
 */
public class BAR2D2Character extends RobotCharacter {

    public BAR2D2Character(int id) {
        super("BAR2-D2", id, 0.0);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
            return new Sprite("bar2d2", "robots2.png", 18, 4, this);
    }
}
