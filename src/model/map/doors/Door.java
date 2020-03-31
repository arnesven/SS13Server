package model.map.doors;

import graphics.sprites.Sprite;

import java.io.Serializable;

public class Door implements Serializable {

     private static final Sprite NORMAL_DOOR = new Sprite("normaldoor", "doors.png", 11, 17, null);
     private static final Sprite LOCKED_DOOR = new Sprite("lockeddoor", "doors.png", 12, 17, null);

    private double x;
    private double y;
    private String name;

    public Door(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Door(double x, double y) {
        this(x, y, "normal");
    }


    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + name;
    }

    public static Door[] makeArrFromDoubleArr(double[] doors) {
        Door[] result = new Door[doors.length/2];
        for (int i = 0; i < result.length; ++i) {
            result[i] = new Door(doors[2*i], doors[2*i+1]);
        }
        return result;
    }

    public static double[] makeDoubleArr(Door[] doors) {
        double[] result = new double[doors.length*2];

        for (int i = 0; i < result.length; i += 2) {
            result[i]   = doors[i/2].getX();
            result[i+1] = doors[i/2].getY();
        }

        return result;
    }

}
