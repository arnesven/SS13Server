package clientview.strategies;

import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;

public class OrbitBackgroundStrategy extends DrawSpaceBackgroundStrategy {
    private final String spriteName;

    public OrbitBackgroundStrategy(String planet) {
        setName("Orbit"+planet);
        this.spriteName = "orbitplanet" + planet + "0";
    }

    @Override
    public void drawBackground(Graphics g, int width, int height) {
        super.drawBackground(g, width, height);
        ImageIcon ic = SpriteManager.getSprite(spriteName);
        g.drawImage(ic.getImage(), (width-ic.getIconWidth())/2, (height-ic.getIconHeight())/2, null);
    }
}
