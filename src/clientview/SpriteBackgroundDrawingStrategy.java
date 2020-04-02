package clientview;

import clientview.strategies.BackgroundDrawingStrategy;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.awt.Graphics;


public abstract class SpriteBackgroundDrawingStrategy extends BackgroundDrawingStrategy {

    private List<ImageIcon> backgroundSprites;

    public SpriteBackgroundDrawingStrategy(String name) {
        super(name);
        backgroundSprites = createSpriteList();
        while (backgroundSprites.size() < 500) {
            backgroundSprites.addAll(backgroundSprites);
        }

        Collections.shuffle(backgroundSprites);
    }

    protected abstract List<ImageIcon> createSpriteList();

    @Override
    public void drawBackground(Graphics g, int width, int height) {
        int counter = 0;
        for (int y = 0; y < height; y += backgroundSprites.get(0).getIconHeight()) {
            for (int x = 0; x < width; x += backgroundSprites.get(0).getIconWidth()) {
                ImageIcon ic = backgroundSprites.get(counter % backgroundSprites.size());
                counter++;
                g.drawImage(ic.getImage(), x, y, null);
            }
        }
    }
}
