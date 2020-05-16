package clientview.strategies;

import java.awt.*;

public abstract class BackgroundDrawingStrategy {

    private final String name;

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
        }
        return new DrawSpaceBackgroundStrategy();
    }

    public abstract void drawBackground(Graphics g, int width, int height);

    public String getName() {
        return name;
    }
}
