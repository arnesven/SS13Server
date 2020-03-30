package clientview;

import java.awt.*;

public abstract class BackgroundDrawingStrategy {
    public static String[] getAllTypesAsStrings() {
        return new String[]{"Black", "Planet", "Space"};
    }

    public static BackgroundDrawingStrategy makeStrategy(String type) {
        if (type.equals("Black")) {
            return new BlackBackgroundStrategy();
        } else if (type.equals("Planet")) {
            return new PlanetBackgroundStrategy();
        }
        return new DrawSpaceBackgroundStrategy();
    }

    public abstract void drawBackground(Graphics g, int width, int height);

}
