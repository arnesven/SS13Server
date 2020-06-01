package shared;

import clientlogic.GameData;
import clientview.OverlaySprite;
import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;

public class ClientExtraEffect {

    private final String effectSpriteName;
    private final String fromOverlayName;
    private final String toOverlayName;
    private double scale;
    private final ImageIcon img;

    public ClientExtraEffect(String effectSpriteName, String fromOverlayName, String toOverlayName) {
        this.effectSpriteName = effectSpriteName;
        this.fromOverlayName = fromOverlayName;
        this.toOverlayName = toOverlayName;

        this.img = SpriteManager.getSprite(effectSpriteName);

        OverlaySprite fromOp = null;
        OverlaySprite toOp = null;
        for (OverlaySprite osp : GameData.getInstance().getOverlaySprites()) {
            if (osp.getSprite().equals(fromOverlayName)) {
                fromOp = osp;
            } else if (osp.getSprite().equals(toOverlayName)) {
                toOp = osp;
            }
        }

        if (fromOp == null || toOp == null) {
            System.out.println("Extra effect sprites not found :-( ");
            return;
        }

        double ax = fromOp.getHitBox().x + fromOp.getHitBox().width  / 2.0;
        double ay = fromOp.getHitBox().y + fromOp.getHitBox().height / 2.0;
        double bx = toOp.getHitBox().x + toOp.getHitBox().width / 2;
        double by = toOp.getHitBox().y + toOp.getHitBox().height / 2;

        double distance = Math.sqrt(Math.pow(bx - ax, 2.0) + Math.pow(by - ay, 2.0));
        this.scale = distance;

    }

    public void drawYourself(Graphics g) {

        //g.drawImage(img.getImage(), toOp.getHitBox().x, toOp.getHitBox().y, null);
    }
}
