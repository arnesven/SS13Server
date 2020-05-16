package graphics.sprites;

import util.Logger;
import util.MyPaths;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
           Sprite sprite = nameMap.get(rest);
            if (sprite != null) {
           //     Logger.log(" found!");
                return encode64(sprite);
            }
            Logger.log(Logger.CRITICAL, "Resource not found! " + rest);


        return "ERROR";
    }

    public static String encode64(Sprite sprite) {
        String str = null;
        try {
            BufferedImage img = sprite.getImage();
            ByteArrayOutputStream bto = new ByteArrayOutputStream();

            ImageIO.write(img, "png", bto);
            byte[] bytes = bto.toByteArray();
            str = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            Logger.log(Logger.CRITICAL, "Exception when reading image! ");
        }

        return str;
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
            try {
                File f = new File(s);
                InputStream is;
                if (f.exists()) {
                    is = new FileInputStream(new File(s));
                } else {
                    is = SpriteManager.class.getResourceAsStream("/" + s.replace("resources/", ""));
                }
                img = ImageIO.read(is);
                filemap.put(s, img);
            } catch (IllegalArgumentException iae) {
                Logger.log("Got exception when reading " + s);
                throw iae;
            }
        }
        return img;
    }

    public static boolean isRegistered(Sprite sprite) {
        return nameMap.containsValue(sprite);
    }
}
