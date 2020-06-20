package model.objects.clawcrane;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.Comparator;

class ClawCrane implements Serializable {

    private static final int HEIGHT = 32;
    private static final int WIDTH = 24;
    private int x;
    private ClawCranePrize grabbedPrize = null;

    public ClawCrane() {
       reset();
    }

    public void drawYourself(Graphics g) {
        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        g.drawLine(x, 0, x, HEIGHT/2);
        if (grabbedPrize == null) {
            g.drawPolyline(new int[]{x - WIDTH / 2, x - WIDTH / 2, x + WIDTH / 2, x + WIDTH / 2},
                    new int[]{HEIGHT, HEIGHT / 2, HEIGHT / 2, HEIGHT}, 4);
        } else {
            g.drawPolyline(new int[]{x-5, x - WIDTH/2, x, x + WIDTH / 2, x+5},
                    new int[]{HEIGHT, HEIGHT/2 + HEIGHT/4, HEIGHT/2, HEIGHT/2 + HEIGHT/4, HEIGHT}, 5);
        }
    }

    public int getXPos() {
        return x;
    }

    public void setXPos(int i) {
        x = i;
        if (grabbedPrize != null) {
            setFromXpos(grabbedPrize);
        }
    }

    public void reset() {
        this.x = getMaximumPosition();
        grabbedPrize = null;

    }

    public int getMaximumPosition() {
        return ClawCraneGame.CONTENTS_WIDTH - WIDTH/2 - 2;
    }

    public Line2D getAimLine(int i) {
        return new Line2D.Double(x+i, 0, x+i, ClawCraneGame.CONTENTS_HEIGHT);
    }


    public Line2D getAimLine() {
        return getAimLine(0);
    }


    public void setGrabbed(ClawCranePrize prize) {
        if (prize != null) {
            setFromXpos(prize);
            this.grabbedPrize = prize;
        }
    }

    private void setFromXpos(ClawCranePrize prize) {
        prize.hitbox.setLocation(new Point(x - 8, HEIGHT - prize.hitbox.height));
    }

    public ClawCranePrize getGrabbedPrize() {
        return grabbedPrize;
    }

    public ClawCranePrize grab(ClawCraneGame game) {
        game.getContents().sort(new Comparator<ClawCranePrize>() {
            @Override
            public int compare(ClawCranePrize c1, ClawCranePrize c2) {
                if (c1.hitbox.y == c2.hitbox.y) {
                    return Math.abs(getXPos() - c1.hitbox.x - c1.hitbox.width/2) -
                            Math.abs(getXPos() - c2.hitbox.x - c1.hitbox.width/2);
                }
                return c1.hitbox.y - c2.hitbox.y;
            }
        });

        ClawCranePrize left = leftGrab(game);
        ClawCranePrize middle = middleGrab(game);
        ClawCranePrize right = rightGrab(game);

        if (middle == null) {
            return null;
        }

        if (left != null) {
            if (left.hitbox.y < middle.hitbox.y) {
                return null;
            }
        }

        if (right != null) {
            if (right.hitbox.y < middle.hitbox.y) {
                return null;
            }
        }

        return middle;
    }

    private ClawCranePrize rightGrab(ClawCraneGame game) {
        for (ClawCranePrize prize : game.getContents()) {
            if (prize.hitbox.intersectsLine(getAimLine(+WIDTH/2))) {
                return prize;
            }
        }
        return null;
    }

    private ClawCranePrize leftGrab(ClawCraneGame game) {
        for (ClawCranePrize prize : game.getContents()) {
            if (prize.hitbox.intersectsLine(getAimLine(-WIDTH/2))) {
                return prize;
            }
        }
        return null;
    }

    private ClawCranePrize middleGrab(ClawCraneGame game) {
        for (ClawCranePrize prize : game.getContents()) {
            if (prize.hitbox.intersectsLine(getAimLine())) {
                return prize;
            }
        }
        return null;
    }
}
