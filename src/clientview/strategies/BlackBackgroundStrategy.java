package clientview.strategies;

import java.awt.*;

public class BlackBackgroundStrategy extends BackgroundDrawingStrategy {
    public BlackBackgroundStrategy() {
        super("Black");
    }

    @Override
    public void drawBackground(Graphics g, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
    }
}
