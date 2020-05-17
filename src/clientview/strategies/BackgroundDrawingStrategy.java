package clientview.strategies;

import java.awt.*;

public abstract class BackgroundDrawingStrategy {

    private String name;

    public BackgroundDrawingStrategy(String name) {
        this.name = name;
    }

    public static String[] getAllTypesAsStrings() {
        return new String[]{"Black", "Planet", "Space", "Orbit-0-0"};
    }

    public static BackgroundDrawingStrategy makeStrategy(String type) {
        if (type.toLowerCase().equals("black")) {
            return new BlackBackgroundStrategy();
        } else if (type.toLowerCase().equals("planet")) {
            return new PlanetBackgroundStrategy();
        } else if (type.toLowerCase().contains("orbit")) {
            return new OrbitBackgroundStrategy(type.toLowerCase().replace("orbit", ""));
        } else if (type.toLowerCase().contains("swoosh")) {
            return new SwooshBackgroundStrategy(type.toLowerCase());
        }
        return new DrawSpaceBackgroundStrategy();
    }

    public abstract void drawBackground(Graphics g, int width, int height);

    public String getName() {
        return name;
    }

    protected void setName(String s) {
        this.name = s;
    }


}
