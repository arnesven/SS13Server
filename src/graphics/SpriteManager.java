package graphics;

import util.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erini02 on 24/04/16.
 */
public class SpriteManager {
    private static final boolean TEST_GRAPHICS = false;
    private static Map<String, Sprite> nameMap = new HashMap<>();
    private static Map<String, BufferedImage> filemap = new HashMap<>();
    private static JFrame frame;
    private static JLabel label;


    public static void register(Sprite sprite) {
        nameMap.put(sprite.getName(), sprite);
        if (TEST_GRAPHICS) {
            test();
        }
    }

    public static String getSpriteAsBas64(String rest) {
        rest = rest.substring(1);
        try {

            Sprite sprite = nameMap.get(rest);
            if (sprite != null) {
           //     Logger.log(" found!");
                BufferedImage img = sprite.getImage();
                ByteArrayOutputStream bto = new ByteArrayOutputStream();
                ImageIO.write(img, "png", bto);

                byte[] bytes = bto.toByteArray();
                String str = Base64.getEncoder().encodeToString(bytes);
                return str;
            }
            Logger.log(Logger.CRITICAL, "Resource not found! " + rest);
        } catch (IOException e) {
            Logger.log(Logger.CRITICAL, "Exception when reading image! " + rest);
        }

        return "ERROR";
    }

    private static void test() {
        if (frame == null) {
            frame = new JFrame("Test");
            frame.setSize(new Dimension(100, 100));
            frame.setVisible(true);
            label = new JLabel();
            frame.getContentPane().add(label);
        }
    }

    public static void testSprite(Sprite sp) {
        try {
            label.setIcon(new ImageIcon(sp.getImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getFile(String s) throws IOException {
        BufferedImage img = filemap.get(s);
        if (img == null) {
            img = ImageIO.read(new File(s));
            filemap.put(s, img);
        }
        return img;
    }
}
