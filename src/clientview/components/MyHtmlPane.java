package clientview.components;

import clientview.SpriteManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHtmlPane extends JEditorPane {
    public MyHtmlPane() {
        this.setContentType("text/html");
        this.setEditable(false);
    }

    @Override
    public void setText(String t) {
        Pattern pattern = Pattern.compile("<img src=\"data:image/png;base64,[\\w\\+/=]*\"></img>");
        Matcher matcher = pattern.matcher(t);
        int uid = 0;
        while (matcher.find()) {
            String datapart = matcher.group().replace("<img src=\"data:image/png;base64,", "");
            datapart = datapart.replace("\"></img>", "");
            System.out.println("Image data is:" + matcher.group());
            BufferedImage buf = SpriteManager.setBase64(datapart);
            try {
                File file = File.createTempFile("ss13img_" + (uid++) + "_", ".png");
                ImageIO.write(buf, "png", file);
                t = t.replace("data:image/png;base64," + datapart, "file://" + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Found images:" + (uid));
        super.setText(t);
    }
}
