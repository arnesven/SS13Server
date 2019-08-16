package graphics.sprites;

import model.Actor;

public interface SpriteObject {

    Sprite getSprite(Actor whosAsking);

    String getPublicName(Actor whosAsking);

}
