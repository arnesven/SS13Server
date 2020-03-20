package clientview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class SmallHeartIcon extends JLabel {

    private static ImageIcon icon = null;


    public SmallHeartIcon() {
        try {
            if (icon == null) {
                icon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("clientresources/images/heart_small.png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIcon(icon);
    }
}
