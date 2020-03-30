package clientview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class DrawSpaceBackgroundStrategy extends BackgroundDrawingStrategy {

    private ArrayList<ImageIcon> spaceSprites;

    public DrawSpaceBackgroundStrategy() {
        spaceSprites = new ArrayList<>();
        for (int i = 0; i < 74; i++) {
            spaceSprites.add(SpriteManager.getSprite("spacebackground" + i + "" + 0));
        }

        for (int i = 0; i < 10; i++) {
            spaceSprites.addAll(spaceSprites);
        }

        Collections.shuffle(spaceSprites);
    }

    @Override
    public void drawBackground(Graphics g, int width, int height) {
            g.setColor(new Color(0x111199));
            g.fillRect(0, 0, width, height);
            int counter = 0;
            for (int y = 0; y < height; y += spaceSprites.get(0).getIconHeight()) {
                for (int x = 0; x < width; x += spaceSprites.get(0).getIconWidth()) {
                    ImageIcon ic = spaceSprites.get(counter % spaceSprites.size());
                    counter++;
                    g.drawImage(ic.getImage(), x, y, null);
                }
            }
    }
}
