package clientview.components;

import clientlogic.GameData;
import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;

public class StyleDrawingArea extends JComponent  {

    private static final int MAGNIFICATION = 7;

    public StyleDrawingArea() {
        setPreferredSize(new Dimension(MAGNIFICATION*MapPanel.getZoom(), 500));
        setMinimumSize(this.getPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        String previewspritename = GameData.getInstance().getStyle().getPreviewSpriteName();
        ImageIcon icon = SpriteManager.getSprite(previewspritename);
        g.drawImage(icon.getImage(), 0, 0, icon.getIconWidth()*MAGNIFICATION, icon.getIconHeight()*MAGNIFICATION,
                0, 0, icon.getIconWidth(), icon.getIconHeight(), null);

    }
}
