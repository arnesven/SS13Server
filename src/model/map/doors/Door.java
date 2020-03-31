package model.map.doors;

import graphics.sprites.Sprite;

import java.io.Serializable;

public abstract class Door implements Serializable {

    private final Sprite sprite = getSprite();

    private double x;
    private double y;
    private String name;


    public Door(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    protected abstract Sprite getSprite();

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
        NormalDoor[] result = new NormalDoor[doors.length/2];
        for (int i = 0; i < result.length; ++i) {
            result[i] = new NormalDoor(doors[2*i], doors[2*i+1]);
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
