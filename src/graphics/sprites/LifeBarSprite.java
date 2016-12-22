package graphics.sprites;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 21/12/16.
 */
public class LifeBarSprite extends Sprite {
    public LifeBarSprite(double amount) {
        super("lifebar"+String.format("%1$.1f", amount), "blank.png",
                0, 0, 24*((int)Math.ceil(amount)), 24, LifeBarSprite.addSpriteLayers(amount));
    }

    private static List<Sprite> addSpriteLayers(double amount) {
        List<Sprite> list = new ArrayList<>();
        int am = (int)Math.floor(amount);
        for (int i = am; i > 0; i--) {
            list.add(new Sprite("heart", "heart.png", 0, 0, 24, 24));
        }
        if ((double)am < amount) {
            list.add(new Sprite("smallheart", "heart_small.png", 0, 0, 24, 24));
        }
        return list;
    }

    @Override
    public BufferedImage getImage() throws IOException {
        BufferedImage buf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics g = buf.getGraphics();
        int x = 0;
        for (Sprite s : getLayers()) {
            g.drawImage(s.getImage(), (x++)*24, 0, null);
        }

        return buf;
    }
}
