package clientview;

import java.awt.*;

public class BlackBackgroundStrategy extends BackgroundDrawingStrategy {
    @Override
    public void drawBackground(Graphics g, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
    }
}
