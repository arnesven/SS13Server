package clientview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class HeartIcon extends JLabel {

    private static ImageIcon image = null;

    public HeartIcon() {
        if (image == null) {
            try {
                image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("clientresources/images/heart.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.setIcon(image);
    }

}
