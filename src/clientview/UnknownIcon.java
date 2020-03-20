package clientview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UnknownIcon extends ImageIcon {

    private static BufferedImage img = null;

    public UnknownIcon() {
        if (img == null) {
            try {
                img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("clientresources/images/unknown.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.setImage(img);
    }
}
