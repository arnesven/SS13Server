package clientview.strategies;

import clientview.SpriteManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwooshBackgroundStrategy extends SpriteBackgroundDrawingStrategy {

    public SwooshBackgroundStrategy(String type) {
        super(type);
    }

    @Override
    protected List<ImageIcon> createSpriteList() {
        ArrayList<ImageIcon> swooshSprites;
        swooshSprites = new ArrayList<>();
        for (int i = 0; i < 225; i++) {
            swooshSprites.add(SpriteManager.getSprite(getName().toLowerCase() + i + "" + 0));
        }
        return swooshSprites;
    }
}
