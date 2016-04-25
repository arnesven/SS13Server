package graphics;

import util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 24/04/16.
 */
public class Sprite {
    private List<Sprite> layers;
    private String name;
    private String mapPath;
    private int row;
    private int column;
    private int width;
    private int height;
    private BufferedImage image;


    public Sprite(String name, String mapPath, int column, int row, int width, int height,
                  List<Sprite> layers){
        this.name = name;
        this.mapPath = mapPath;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
        this.layers = layers;
        SpriteManager.register(this);
        try {
            getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sprite(String name, String mapPath, int column, int row, int width, int height){
        this(name, mapPath, column, row, width, height, new ArrayList<>());
    }


    public Sprite(String name, String mapPath, int column, int row) {
        this(name, mapPath, column, row, 32, 32);
    }

    public Sprite(String name, String mapPath, int column) {
        this(name, mapPath, column, 0);
    }

    public Sprite(String name, List<Sprite> list) {
        this(name, list.get(0).getMap(), list.get(0).getColumn(),
                list.get(0).getRow(), list.get(0).getWidth(),
                list.get(0).getHeight(), list.subList(1, list.size()));
    }

    public Sprite(String name, String mapPath, int column, List<Sprite> list) {
        this(name, mapPath, column, 0, 32, 32, list);
    }


    public String getName(){
        return name;
    }

    public BufferedImage getImage() throws IOException {
        if (this.image == null) {
            this.image = internalGetImage();
        }
        return this.image;
     }

    protected BufferedImage internalGetImage() throws IOException {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = result.getGraphics();
        BufferedImage img = ImageIO.read(new File("resources/sprites/" + mapPath));
        img = img.getSubimage(column * width, row * height, width, height);
        g.drawImage(img, 0, 0, null);

        for (Sprite s : layers) {
            g.drawImage(s.internalGetImage(), 0, 0, null);
        }

        return result;
    }


    public String getMap() {
        return mapPath;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
