package graphics;

import util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by erini02 on 24/04/16.
 */
public class Sprite {
    private String name;
    private String mapPath;
    private int row;
    private int column;
    private int width;
    private int height;


    public Sprite(String name, String mapPath, int column, int row, int width, int height){
        this.name = name;
        this.mapPath = mapPath;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
        SpriteManager.register(this);
    }

    public Sprite(String name, String mapPath, int column, int row) {
        this(name, mapPath, column, row, 32, 32);
    }

    public Sprite(String name, String mapPath, int column) {
        this(name, mapPath, column, 0);
    }



    public String getName(){
        return name;
    }

    public BufferedImage getImage() throws IOException {
        BufferedImage img = ImageIO.read(new File("resources/sprites/" + mapPath));
//        BufferedImage buf = new BufferedImage(32, 32, BufferedImage.TYPE_4BYTE_ABGR);
//        Graphics g = buf.getGraphics();
//        g.drawImage(img,
//                    0, 0, width, height,  // destination (x y), (x y)
//                    column*width, row*height, (column+1)*width, (height+1)*height,
//                    null);

        return img.getSubimage(column*width, row*height, width, height);

     //   return buf;
    }
}
