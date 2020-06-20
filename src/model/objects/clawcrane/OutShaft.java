package model.objects.clawcrane;

import graphics.sprites.Sprite;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;

class OutShaft implements Serializable {
    private ClawCraneGame clawCraneGame;
    private final Sprite outShaft;
    private final Rectangle hitbox;

    public OutShaft(ClawCraneGame clawCraneGame) {
        this.clawCraneGame = clawCraneGame;
        this.outShaft = new Sprite("outshaft", "arcade.png", 6, 7, null);
        this.hitbox = new Rectangle(clawCraneGame.CONTENTS_WIDTH -32, clawCraneGame.CONTENTS_HEIGHT -32, 32, 32);
    }

    public void drawYousrelf(Graphics2D g) {
        try {
            g.drawImage(outShaft.getImage(), hitbox.x, hitbox.y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Rectangle2D getHitbox() {
        return hitbox;
    }
}
