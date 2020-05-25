package graphics.sprites;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.Logger;
import util.MyPaths;

/**
 * Created by erini02 on 24/04/16.
 */
public class Sprite implements Serializable {

    private SpriteObject objectReference;
    private List<Sprite> layers;
    private String name;
    private String mapPath;
    private int row;
    private int column;
    private int width;
    private int height;
    private int resizeWidth;
    private int resizeHeight;
    private Color color;
    private double rotation = 0.0;
    private int frames;
    private boolean looping;
    private int downShift = 0;


    public Sprite(String name, String mapPath, int column, int row, int width, int height,
                  List<Sprite> layers, SpriteObject objectRef){
        this.name = name;
        this.mapPath = mapPath;
        this.row = row;
        this.column = column;
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        this.layers = layers;
        this.resizeWidth = width;
        this.resizeHeight = height;
        this.objectReference = objectRef;
        frames = 1;
        this.looping =false;
        SpriteManager.register(this);
        try {
            getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean allLooping() {
        for (Sprite sp : layers) {
            if (!isLooping()) {
                return false;
            }
        }
        return true;
    }

    public Sprite(Sprite other, String suffix) {
        this(other.name + suffix, other.mapPath, other.getColumn(), other.getRow(), other.getWidth(), other.getHeight(), other.getLayers(), other.objectReference);
    }

    public static Sprite blankSprite() {
        return new Sprite("dummy", "animal.png", 0, null);
    }


    protected List<Sprite> getLayers() {
        return layers;
    }


    public Sprite(String name, String mapPath, int column, int row, int width, int height, SpriteObject objectRef){
        this(name, mapPath, column, row, width, height, new ArrayList<>(), objectRef);
    }


    public Sprite(String name, String mapPath, int column, int row, SpriteObject objectRef) {
        this(name, mapPath, column, row, 32, 32, objectRef);
    }

    public Sprite(String name, String mapPath, int column, int row, int width, int height, int resizeW, int resizeH, SpriteObject objectRef) {
        this(name, mapPath, column, row, width, height, objectRef);
        this.resizeWidth = resizeW;
        this.resizeHeight = resizeH;
    }

    public Sprite(String name, String mapPath, int column, SpriteObject objectRef) {
        this(name, mapPath, column, 0, objectRef);
    }

    public Sprite(String name, List<Sprite> list) {
        this(name, list.get(0).getMap(), list.get(0).getColumn(),
                list.get(0).getRow(), list.get(0).getWidth(),
                list.get(0).getHeight(), new ArrayList<Sprite>(list.subList(1, list.size())),
                list.get(0).objectReference);
    }

    public Sprite(String name, String mapPath, int column, List<Sprite> list, SpriteObject objectRef) {
        this(name, mapPath, column, 0, 32, 32, list, objectRef);
    }

    public String getName(){
        return name + (int)getRotation();
    }

    public BufferedImage getImage() throws IOException {
        return internalGetImage();
     }

    protected BufferedImage internalGetImage() throws IOException {
        int maxFrames = findMaxFrames();

        BufferedImage result = new BufferedImage(width*maxFrames, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = result.getGraphics();
        BufferedImage img = SpriteManager.getFile(MyPaths.makePath(new String[]{"resources", "sprites"}) + mapPath);
        img = img.getSubimage(column * width, row * height, width*frames, height);

        Graphics2D g2d = (Graphics2D) g;

        double rotationRequired = Math.toRadians(getRotation());
        double locationX = img.getWidth() / 2;
        double locationY = img.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

// Drawing the rotated image at the required drawing locations
        for (int n = 0; n < maxFrames; ++n) {
            g2d.drawImage(op.filter(img, null), n*img.getWidth(), downShift, null);
        }
        for (Sprite s : layers) {
            for (int n = 0; n < maxFrames; ++n) {
                g2d.drawImage(op.filter(s.internalGetImage(), null), n * (s.getWidth()*s.getFrames()), downShift, null);
            }
        }

        if (color != null) {
            result = colorize(result, color);
        }

        if (this.resizeWidth != this.width || this.resizeHeight != this.height) {
            // image needs to be resized!
            result = resize(result);
        }

        return result;
    }

    private int findMaxFrames() {
        int max = frames;
        for (Sprite s : layers) {
            if (s.frames > max) {
                max = s.frames;
            }
        }
        return max;
    }

    private BufferedImage resize(BufferedImage result) {
        Logger.log("resizeing an image from " + width + "x" + height + " to " + resizeWidth + "x" + resizeHeight);
        BufferedImage dimg = new BufferedImage(this.resizeWidth, this.resizeHeight, result.getType());
        Graphics2D gnew = dimg.createGraphics();
        gnew.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gnew.drawImage(result, 0, 0,
                this.resizeWidth, this.resizeHeight,
                0, 0,
                this.width, this.height, null);
        gnew.dispose();
        return dimg;
    }


    private static BufferedImage colorize(BufferedImage original, Color color) {
        BufferedImage redVersion = new BufferedImage(original.getWidth(), original.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) redVersion.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, original.getWidth(), original.getHeight());

        g.setComposite(AlphaComposite.DstIn);
        g.drawImage(original, 0, 0, null);
        return redVersion;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void addToOver(Sprite piratemask) {
        layers.add(piratemask);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double d) {
        rotation = d;
        SpriteManager.register(this);
        try {
            getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Sprite makeSpectatorItems(List<Sprite> spriteList) {
        StringBuilder bldr = new StringBuilder();
        for (Sprite sp : spriteList) {
            bldr.append(sp.getName());
        }
        int size = 32;
        if (spriteList.size() > 1) {
            size += 32;
        }
        if (spriteList.size() > 2) {
            size += (spriteList.size() - 2)*16;
        }
        Sprite sp = new SpectatorSprite(bldr.toString(), "blank.png", 0, 0, size, 32, spriteList);
        return sp;
    }

    public void setObjectRef(SpriteObject obj) {
        this.objectReference = obj;
    }

    public SpriteObject getObjectReference() {
        return objectReference;
    }

    public int getFrames() {
        return frames;
    }

    protected void setFrames(int frames) {
        this.frames = frames;
    }

    public void setLooping(boolean b) {
        this.looping = b;
    }

    public boolean isLooping() {
        return looping;
    }

    public void registerYourself() {
        SpriteManager.register(this);
        try {
            getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRegistered() {
        return SpriteManager.isRegistered(this);
    }

    public void setColumn(int i) {
        this.column = i;
    }


    public int getMaximumFrames() {
        return findMaxFrames();
    }

    public void shiftUpPx(int i) {
        downShift = -i;
    }

    public Sprite copy() {
        Sprite toReturn = new Sprite(name, mapPath, column, row, width, height, layers, objectReference);
        toReturn.setColor(color);
        toReturn.setRotation(rotation);
        return toReturn;
    }
}
