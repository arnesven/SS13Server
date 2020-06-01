package graphics;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;

import java.io.Serializable;

public class ExtraEffect implements Serializable {
    private final SpriteObject from;
    private final SpriteObject to;
    private final Sprite spriteToUse;

    public ExtraEffect(SpriteObject from, SpriteObject to, Sprite spriteToUse) {
        this.from = from;
        this.to = to;
        this.spriteToUse = spriteToUse;
    }

    public String getStringRepresentation(Actor forWhom) {
        return spriteToUse.getName() + "<eepart>" + from.getSprite(forWhom).getName() + "<eepart>" + to.getSprite(forWhom).getName();
    }
}
