package model.objects.decorations;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class SolarPanel extends GameObject {

    private static int panelID = 100;
    public static final double SKEWED_ALIGNED_KW = 0.238;  // 238 W
    public static double PERPENDICULAR_ALIGNED_KW = 0.511; // 511 W
    private final int number;
    private int rotation;
    //private static final int[] SPRITE_MAP_ANGLES     = new int[]{2, 0, 4, 7, 5, 1, 3, 6};
    private static final int[] REV_SPRITE_MAP_ANGLES = new int[]{1, 5, 0, 6, 2, 4, 7, 3};

    public SolarPanel(Room position, int initialRotation) {
        super("Solar Panel", position);
        this.number = panelID++;
        setRotation(initialRotation);
    }

    public SolarPanel(Room pos) {
        this(pos, MyRandom.nextInt(8));
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        List<Sprite> sprs = new ArrayList<>();

        int column = REV_SPRITE_MAP_ANGLES[rotation];
        int row = 3;
        if (column == 7) {
            column = 12;
            row = 2;
        }
        sprs.add(new Sprite("solarpanel", "power.png", column, row, 32,32,this));
        return new Sprite("solarpanalbase" + rotation, "power.png", 8, 3, 32,32, sprs, this);
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int selectedAngle) {
        this.rotation = selectedAngle;
    }

    public double getPower(GameData gameData) {
        if (getRotation() == gameData.getMap().getSunAngle()) {
            return SolarPanel.PERPENDICULAR_ALIGNED_KW * 0.001;
        } else if ((getRotation() + 1) % 8 == gameData.getMap().getSunAngle() ||
                (getRotation() + 7) % 8 == gameData.getMap().getSunAngle()) {
            return SolarPanel.SKEWED_ALIGNED_KW * 0.001;
        }
        return 0.0;
    }

    public int getNumber() {
        return number;
    }
}
