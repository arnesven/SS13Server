package graphics.sprites;

import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class CombinedSprite extends Sprite {
    private final List<List<Sprite>> spriteMatrix;

    public CombinedSprite(String name, List<List<Sprite>> sprs) {
        super(name, "human.png", 0, null);
        this.spriteMatrix = sprs;
        SpriteManager.register(this);
    }

    @Override
    public BufferedImage getImage() throws IOException {
        if (spriteMatrix == null) {
            return super.getImage();
        }

        int width = spriteMatrix.get(0).size()*32;
        int height = spriteMatrix.size()*32;
        Logger.log(Logger.CRITICAL, "Making combined sprite size: " + width + "x" + height + "px, " + spriteMatrix.toString());

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        for (int y = 0; y < spriteMatrix.size(); ++y) {
            for (int x = 0; x < spriteMatrix.get(0).size(); ++x) {
                g.drawImage(spriteMatrix.get(y).get(x).getImage(),
                        x*32, y*32, (x+1)*32, (y+1)*32,
                        0, 0, 32, 32, null);
            }
        }
        return img;
    }
}
