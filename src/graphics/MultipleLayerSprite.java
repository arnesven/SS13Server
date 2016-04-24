package graphics;

import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 24/04/16.
 */
public class MultipleLayerSprite extends Sprite {

    private List<Sprite> ontops = new ArrayList<>();
    private boolean firstTime = true;

    public MultipleLayerSprite(String manfireprotection, String s, int i) {
        super(manfireprotection, s, i);


    }

    public void addOntop(Sprite body) {
        ontops.add(body);
    }

    @Override
    public BufferedImage getImage() throws IOException {
        BufferedImage img = super.getImage();
        if (firstTime) {

            Graphics g = img.getGraphics();
            for (Sprite sp : ontops) {
                g.drawImage(sp.getImage(), 0, 0, null);
                Logger.log("Adding stuff ontop");
            }
            firstTime = false;
       }
        return img;
    }
}
