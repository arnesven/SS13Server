package model.events.animation;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;

import java.util.List;

public class AnimatedSprite extends Sprite {

    public AnimatedSprite(String name, String mapPath, int column, int row,
                          int width, int height, SpriteObject objectRef, int frames, boolean looping) {
        super(name, mapPath, column, row, width, height, objectRef);
        setFrames(frames);
        setLooping(looping);
    }
}
