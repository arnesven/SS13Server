package shared;

import clientlogic.ClientDoor;
import clientlogic.GameData;
import clientlogic.MouseInteractable;
import clientlogic.Room;
import clientview.OverlaySprite;
import clientview.SpriteManager;
import clientview.animation.AnimationHandler;

import javax.swing.*;
import java.awt.*;

public class ClientExtraEffect {

    private final String effectSpriteName;
    private final String fromOverlayName;
    private final String toOverlayName;
    private final int spriteWidth;
    private final int spriteHeight;
    private final int frames;
    private final boolean looping;
    private int currentFrame;
    private MouseInteractable fromOp;
    private MouseInteractable toOp;
    private double xCenter;
    private double yCenter;
    private double scale;

    public ClientExtraEffect(String effectSpriteName, String fromOverlayName, String toOverlayName,
                             int spriteWidth, int spriteHeight, int frames, boolean looping) {
        this.effectSpriteName = effectSpriteName;
        this.fromOverlayName = fromOverlayName;
        this.toOverlayName = toOverlayName;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.frames = frames;
        this.looping = looping;
        this.currentFrame = 0;


        fromOp = null;
        toOp = null;
        for (OverlaySprite osp : GameData.getInstance().getOverlaySprites()) {
            if (osp.getEffectName().equals(fromOverlayName)) {
                fromOp = osp;
            } else if (osp.getEffectName().equals(toOverlayName)) {
                toOp = osp;
            }
        }

        for (Room r : GameData.getInstance().getMiniMap()) {
            if (r.getDoors() != null) {
                for (ClientDoor d : r.getDoors()) {
                    if (d.getName().equals(toOverlayName)) {
                        System.out.println("Found door toOp");
                        toOp = d;
                    }
                    if (d.getName().equals(fromOverlayName)) {
                        System.out.println("Found door fromop");
                        fromOp = d;
                    }
                }
            }
        }

        if (fromOp == null || toOp == null) {
            System.out.println("Extra effect sprites not found :-( ");
            return;
        }


    }

    public void drawYourself(Graphics g) {
        if (fromOp == null || toOp == null) {
            return;
        }
        double ax = fromOp.getHitBox().x + fromOp.getHitBox().width  / 2.0;
        double ay = fromOp.getHitBox().y + fromOp.getHitBox().height / 2.0;
        double bx = toOp.getHitBox().x + toOp.getHitBox().width / 2;
        double by = toOp.getHitBox().y + toOp.getHitBox().height / 2;

        double dx = bx - ax;
        double dy = by - ay;

        double distance = Math.sqrt(Math.pow(bx - ax, 2.0) + Math.pow(by - ay, 2.0));
        this.scale = distance;

        xCenter = ax + dx/2.0;
        yCenter = ay + dy/2.0;

        double angle = Math.atan2(dy, dx) + Math.PI / 2.0;

        Graphics2D g2d = (Graphics2D)g;

        ImageIcon img = SpriteManager.getSprite(effectSpriteName + "frame" + currentFrame + "0");

        g2d.rotate(angle, xCenter, yCenter);
        g2d.drawImage(img.getImage(),
                (int)(xCenter - spriteWidth/2),
                (int)(yCenter - distance/2),
                (int)(xCenter + spriteWidth/2),
                (int)(yCenter + distance/2),
                0, 0,
                spriteWidth,
                spriteHeight,
                null);
        g2d.rotate(-angle, xCenter, yCenter);
        if (looping) {
            currentFrame = AnimationHandler.getState() % frames;
        } else if (currentFrame < frames-1) {
            currentFrame++;
        }
    }
}
