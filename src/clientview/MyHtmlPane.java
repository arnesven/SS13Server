package clientview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyHtmlPane extends JEditorPane {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Ran");
        try {
            BufferedImage ic = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("clientresources/images/heart.png"));
            if (ic != null) {
                g.drawImage(ic, 50, 50, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
