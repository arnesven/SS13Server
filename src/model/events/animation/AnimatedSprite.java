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

    public AnimatedSprite(String name, String mapPath, int column, int row,
                          int width, int height, List<Sprite> list, SpriteObject objectRef, int frames, boolean looping) {
        super(name, mapPath, column, row, width, height, list, objectRef);
        setFrames(frames);
        setLooping(looping);
    }

    public AnimatedSprite(String shuttingfiredoorani, List<Sprite> sps, int i, boolean b) {
        super(shuttingfiredoorani, sps);
        setFrames(i);
        setLooping(b);
    }

    public static Sprite blankAnimationSprite() {
        return new Sprite("blankani", "blankani.png", 0, null);
    }

    public AnimatedSprite copy() {
        return new AnimatedSprite(getName(), getMap(), getColumn(), getRow(), getWidth(), getHeight(), getObjectReference(), getFrames(), isLooping());
    }
}
