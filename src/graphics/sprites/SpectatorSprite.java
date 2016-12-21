package graphics.sprites;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Created by erini02 on 20/12/16.
 */
public class SpectatorSprite extends Sprite {

    public SpectatorSprite(String s, String s1, int i, int i1, int i2, int i3, List<Sprite> spriteList) {
        super(s, s1, i, i1, i2, i3, spriteList);
    }

    @Override
    public BufferedImage getImage() throws IOException {
        BufferedImage bi =  new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();

        int x = 0;
        for (Sprite sp : getLayers()) {
            g.drawImage(sp.getImage(), x, 0, sp.getWidth(), sp.getHeight(), null);
            if (x == 0) {
                x += 32;
            } else {
                x += 16;
            }
        }

        return bi;
    }
}
