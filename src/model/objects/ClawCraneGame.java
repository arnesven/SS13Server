package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.actions.objectactions.WalkUpToSinglePersonUseMachineAction;
import model.events.animation.AnimatedSprite;
import model.fancyframe.ClawCraneGameFancyFrame;
import model.fancyframe.FancyFrame;
import model.items.CapsulePrize;
import model.items.RandomItemManager;
import model.items.general.GameItem;
import model.map.rooms.LoungeRoom;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import util.MyRandom;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClawCraneGame extends SinglePersonUseMachine {
    private final int contentsWidth;
    private final int contentsHeight;
    private List<ClawCranePrize> contents;
    private static final int HITBOX_SIZE = 16;

    public ClawCraneGame(Room room) {
        super("Claw Crane Game", room);
        this.contentsWidth = 200;
        this.contentsHeight = 150;
        generateContents();
        setPowerPriority(0);

    }

    private void generateContents() {
        this.contents = new ArrayList<>();
        for (int i = 10; i > 0; --i) {
            contents.add(new ClawCranePrize(RandomItemManager.getRandomSearchItem()));
        }
        for (int i = 15; i > 0; --i) {
            contents.add(new ClawCraneCapsulePrize());
        }
    }


    @Override
    public double getPowerConsumption() {
        return super.getPowerConsumption()*0.5;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isPowered()) {
            return new AnimatedSprite("clawcrane", "arcade.png", 3, 2, 32, 32, this, 5, true);
        } else if (isBroken()) {
            return new Sprite("clawcranebroken", "arcade.png", 2, 2, this);
        }
        return new Sprite("clawcranenopower", "arcade.png", 5, 7, this);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl instanceof Player) {
            at.add(new WalkUpToSinglePersonUseMachineAction((Player)cl, gameData, this) {
                @Override
                protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
                    return  new ClawCraneGameFancyFrame((Player)cl, gameData, ClawCraneGame.this);
                }
            });

        }
    }

    public BufferedImage makeImageFromContents() {
        BufferedImage img = new BufferedImage(contentsWidth, contentsHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(new Color(204, 255, 255));
        g.fillRect(0, 0, contentsWidth, contentsHeight);
        for (ClawCranePrize prize : contents) {
            //g.rotate(prize.rotation, prize.hitbox.x+(prize.hitbox.getWidth())/2, prize.hitbox.y+(prize.hitbox.getHeight()/2));
            try {
                g.drawImage(prize.item.getSprite(null).getImage(), prize.hitbox.x, prize.hitbox.y, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //g.rotate(-prize.rotation, prize.hitbox.x+(prize.hitbox.getWidth())/2, prize.hitbox.y+(prize.hitbox.getHeight()/2));
        }
        return img;
    }

    private class ClawCranePrize implements Serializable {
        public Rectangle hitbox;
        public final GameItem item;
        public final double rotation;

        public ClawCranePrize(GameItem randomSearchItem) {
            int currY = contentsHeight-32;
            int x = MyRandom.nextInt(contentsWidth - 32);
            for (int i = 1000; i > 0; --i) {
                this.hitbox =new Rectangle(x, currY, HITBOX_SIZE, HITBOX_SIZE);
                if (!isColliding()) {
                    break;
                } else {
                    if (i < 100) {
                        x = MyRandom.nextInt(contentsWidth - 32);
                    } else {
                        if (currY < 60) {
                            currY = contentsHeight - 32;
                            x = MyRandom.nextInt(contentsWidth - 32);
                        } else {
                            currY -= HITBOX_SIZE;
                        }
                    }
                }
            }
            this.item = randomSearchItem;
            this.rotation = MyRandom.nextDouble()*Math.PI*2.0;
        }

        public boolean isColliding() {
            for (ClawCranePrize other : contents) {
                if (other != this) {
                    if (other.hitbox.intersects(this.hitbox)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private class ClawCraneCapsulePrize extends ClawCranePrize {
        public ClawCraneCapsulePrize() {
            super(new CapsulePrize());
        }
    }
}
