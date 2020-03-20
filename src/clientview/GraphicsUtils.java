package clientview;

import java.awt.*;

public class GraphicsUtils {
    public static Stroke getThickStroke() {
        return new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    public static Stroke getThinStroke() {
        return new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    public static Color getDefaultBackgroundColor() {
        return Color.LIGHT_GRAY;
    }

    public static Color getThickStrokeColor() {
        return Color.DARK_GRAY;
    }

    public static Color getThinStrokeColor() {
        return Color.GRAY;
    }

    @Deprecated
    public static void drawContourString(Graphics g, String text, int x, int y, Color contourColor, int contourWidth) {
        Font upFont = g.getFont();
        Font downFont = new Font(upFont.getName(), upFont.getStyle(), upFont.getSize() + contourWidth);
        Color upColor = g.getColor();
        for (int i = 0; i < text.length(); ++i) {
            g.setColor(contourColor);
            g.setFont(downFont);
            String oneChar = text.charAt(i) + "";
            g.drawString(oneChar, x, y);

            int xOff = g.getFontMetrics().stringWidth(oneChar);
            int yOff = g.getFontMetrics().getHeight();
            g.setColor(upColor);
            g.setFont(upFont);
            xOff = (xOff - g.getFontMetrics().stringWidth(oneChar)) / 2;
            yOff = (yOff - g.getFontMetrics().getHeight()) / 2;
            g.drawString(oneChar, x + xOff, y - yOff);
            x = x + g.getFontMetrics().stringWidth(oneChar);
        }


    }

    public static Color getDefaultTextBackground() {
        return Color.BLACK;
    }

    public static Color getDefaultTextForeground() {
        return Color.YELLOW;
    }
}
