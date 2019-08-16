package graphics.sprites;

import model.Actor;

public class BlurredCharacter implements SpriteObject {
    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("blurredchar", "human.png", 138, this);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Somebody";
    }
}
