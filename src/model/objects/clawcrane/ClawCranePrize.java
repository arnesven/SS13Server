package model.objects.clawcrane;

import model.items.general.GameItem;
import util.MyRandom;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

class ClawCranePrize implements Serializable {
    private ClawCraneGame clawCraneGame;
    public Rectangle hitbox;
    public final GameItem item;
    public final double rotation;

    public ClawCranePrize(ClawCraneGame clawCraneGame, GameItem randomSearchItem) {
        this.clawCraneGame = clawCraneGame;
        int currY = ClawCraneGame.CONTENTS_HEIGHT -32;
        int spaceForShaft = 32 + ClawCraneGame.HITBOX_SIZE;
        int maximumX = ClawCraneGame.CONTENTS_WIDTH - spaceForShaft;
        int x = MyRandom.nextInt(maximumX);
        for (int i = 1000; i > 0; --i) {
            this.hitbox =new Rectangle(x, currY, ClawCraneGame.HITBOX_SIZE, ClawCraneGame.HITBOX_SIZE);
            if (!isColliding()) {
                break;
            } else {
                if (i < 100) {
                    x = MyRandom.nextInt(maximumX);
                } else {
                    if (currY < 60) {
                        currY = ClawCraneGame.CONTENTS_HEIGHT - ClawCraneGame.HITBOX_SIZE;
                        x = MyRandom.nextInt(maximumX);
                    } else {
                        currY -= ClawCraneGame.HITBOX_SIZE;
                    }
                }
            }
        }
        this.item = randomSearchItem;
        this.rotation = MyRandom.nextDouble()*Math.PI*2.0;
    }

    public boolean isColliding() {
        for (ClawCranePrize other : clawCraneGame.getContents()) {
            if (other != this) {
                if (other.hitbox.intersects(this.hitbox)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void drawYourself(Graphics2D g) {
        int offset = (item.getSprite(null).getWidth() - ClawCraneGame.HITBOX_SIZE) / 2;
        try {
            g.drawImage(item.getSprite(null).getImage(), hitbox.x-offset, hitbox.y-offset, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setColor(Color.RED);
       // g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
}
