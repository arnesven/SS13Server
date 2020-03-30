package clientview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlanetBackgroundStrategy extends SpriteBackgroundDrawingStrategy {

    @Override
    protected List<ImageIcon> createSpriteList() {
        ArrayList<ImageIcon> spaceSprites;
        spaceSprites = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            spaceSprites.add(SpriteManager.getSprite("planetbackground" + i + "" + 0));
        }
        return spaceSprites;
    }
}
