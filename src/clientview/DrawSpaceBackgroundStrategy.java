package clientview;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DrawSpaceBackgroundStrategy extends SpriteBackgroundDrawingStrategy {


    @Override
    protected List<ImageIcon> createSpriteList() {
        ArrayList<ImageIcon> spaceSprites;
        spaceSprites = new ArrayList<>();
        for (int i = 0; i < 74; i++) {
            spaceSprites.add(SpriteManager.getSprite("spacebackground" + i + "" + 0));
        }
        return spaceSprites;
    }

}
